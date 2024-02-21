package com.example.lms.services;

import com.example.lms.exception.TxnException;
import com.example.lms.models.*;
import com.example.lms.repository.TrxRepository;
import com.example.lms.request.TxnCreateRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrxService {

    @Autowired
    private TrxRepository trxRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private BookService bookService;

    @Transactional(rollbackOn = {TxnException.class})
    public String createTransaction(TxnCreateRequest txnCreateRequest) throws TxnException {

        List<Student> studentList=studentService.filter(StudentFilterType.CONTACT, Operator.EQUALS,txnCreateRequest.getStudentContact());

        if(studentList==null || studentList.isEmpty()){
            throw new TxnException("Student does not belong to my library.");
        }
        Student studentFromDb=studentList.get(0);

        List<Book> bookList=bookService.filter(FilterType.BOOK_NO, Operator.EQUALS,txnCreateRequest.getBookNo());

        if(bookList==null || bookList.isEmpty()){
            throw new TxnException("Book does not belong to my library.");
        }

        Book bookFromLib=bookList.get(0);
        if(bookFromLib.getStudent()!=null){
            throw new TxnException("Book is already assigned to someone.");
        }
        String trxId= UUID.randomUUID().toString();
        Txn trx= Txn.builder()
                .student(studentFromDb)
                .book(bookFromLib)
                .txnId(trxId)
                .status(TrxStatus.ISSUED)
                .build();
        trx=trxRepository.save(trx);
        bookFromLib.setStudent(studentFromDb);
        bookService.saveUpdate(bookFromLib);
        return trx.getTxnId();
    }
}
