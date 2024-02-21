package com.example.lms.controller;

import com.example.lms.exception.TxnException;
import com.example.lms.models.Txn;
import com.example.lms.request.TxnCreateRequest;
import com.example.lms.services.TrxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
