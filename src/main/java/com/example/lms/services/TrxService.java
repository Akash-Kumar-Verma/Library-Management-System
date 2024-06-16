package com.example.lms.services;

import com.example.lms.exception.TxnException;
import com.example.lms.models.*;
import com.example.lms.repository.TxnRepository;
import com.example.lms.request.TxnCreateRequest;
import com.example.lms.request.TxnReturnRequest;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrxService {

    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private BookService bookService;

    @Value("${student.valid.days}")
    private int validDays;

    @Value("${student.delayedFine.findPerDay}")
    private int finePerDay;

    public Student filterStudent(StudentFilterType type, Operator operator, String value) throws TxnException {
        List<Student> studentList =  studentService.filter(type, operator, value);
        if(studentList == null || studentList.isEmpty()){
            throw new TxnException("Student does not belong to my library.");
        }
        Student studentFromDB = studentList.get(0);
        return studentFromDB;
    }

    public Book filterBook(FilterType type, Operator operator, String value) throws TxnException {
        List<Book> bookList =  bookService.filter(type, operator, value);
        if(bookList == null || bookList.isEmpty() ){
            throw new TxnException("Book does not belong to my library.");
        }
        Book bookFromLib = bookList.get(0);
        return bookFromLib;
    }

    @Transactional(rollbackOn = {TxnException.class})
    public String createTransaction(TxnCreateRequest txnCreateRequest,Student student) throws TxnException {

        Student studentFromDb = filterStudent(StudentFilterType.CONTACT, Operator.EQUALS, student.getPhoneNo());

        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operator.EQUALS, txnCreateRequest.getBookNo());

        if (bookFromLib.getStudent() != null) {
            throw new TxnException("Book is already assigned to someone.");
        }
        String trxId = UUID.randomUUID().toString();
        Txn trx = Txn.builder()
                .student(studentFromDb)
                .book(bookFromLib)
                .txnId(trxId)
                .paidAmount(txnCreateRequest.getAmount())
                .status(TrxStatus.ISSUED)
                .build();
        trx = txnRepository.save(trx);
        bookFromLib.setStudent(studentFromDb);
        bookService.saveUpdate(bookFromLib);
        return trx.getTxnId();
    }

    @Transactional(rollbackOn = {TxnException.class})
    public int returnBook(TxnReturnRequest txnReturnRequest) throws TxnException {

        Student studentFromDB = filterStudent(StudentFilterType.CONTACT, Operator.EQUALS, txnReturnRequest.getStudentContact());

        Book bookFromLib = filterBook(FilterType.BOOK_NO, Operator.EQUALS, txnReturnRequest.getBookNo());

        if(bookFromLib.getStudent() != null && bookFromLib.getStudent().equals(studentFromDB)){

            Txn txnFromDb =  txnRepository.findByTxnId(txnReturnRequest.getTxnId());

            if(txnFromDb == null){
                throw new TxnException("No txn has been found with this txnid.");
            }

            int amount = calculateSettlementAmount(txnFromDb);
            if (amount == txnFromDb.getPaidAmount()) {
                txnFromDb.setStatus(TrxStatus.RETURNED);
            } else {
                txnFromDb.setStatus(TrxStatus.FINED);
            }
            txnFromDb.setPaidAmount(amount);
            bookFromLib.setStudent(null);
            bookService.saveUpdate(bookFromLib);

            return amount;

        } else {
            throw new TxnException("Book is either not assigned to anyone,or assigned to someone else. ");
        }
    }

    private int calculateSettlementAmount(Txn txnFromDB) {
        long issueTime = txnFromDB.getCreatedOn().getTime();
        long returnTime = System.currentTimeMillis();
        long timeDiff = returnTime - issueTime;

        int dayPassed = (int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        if (dayPassed > validDays) {
            int finedAmount = (dayPassed - validDays) * finePerDay;
            return txnFromDB.getPaidAmount() - finedAmount;
        }

        return txnFromDB.getPaidAmount();
    }
}
