package com.example.lms.controller;

import com.example.lms.exception.TxnException;
import com.example.lms.models.Student;
import com.example.lms.request.TxnCreateRequest;
import com.example.lms.request.TxnReturnRequest;
import com.example.lms.services.TrxService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/txn")
public class TxnController {

    @Autowired
    private TrxService trxService;

    @PostMapping("/create")
    private String createTransaction(@RequestBody @Valid TxnCreateRequest txnCreateRequest) throws TxnException {
        // Validation should be here
        System.out.println("Here is Create request initiated");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student =  (Student) authentication.getPrincipal();
        System.out.println("Student authentication "+student);
        return trxService.createTransaction(txnCreateRequest,student);
    }
    @PutMapping("/return")
    private int returnBook(@RequestBody TxnReturnRequest txnReturnRequest) throws TxnException {
        // Validation should be here
        return trxService.returnBook(txnReturnRequest);

    }
}
