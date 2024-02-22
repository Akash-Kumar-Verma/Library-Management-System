package com.example.lms.controller;

import com.example.lms.exception.TxnException;
import com.example.lms.request.TxnCreateRequest;
import com.example.lms.request.TxnReturnRequest;
import com.example.lms.services.TrxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/txn")
public class TxnController {

    @Autowired
    private TrxService trxService;

    @PostMapping("/create")
    private String createTransaction(@RequestBody TxnCreateRequest txnCreateRequest) throws TxnException {
        // Validation should be here

        return trxService.createTransaction(txnCreateRequest);

    }
    @PutMapping("/return")
    private int returnBook(@RequestBody TxnReturnRequest txnReturnRequest) throws TxnException {
        // Validation should be here
        return trxService.returnBook(txnReturnRequest);

    }
}
