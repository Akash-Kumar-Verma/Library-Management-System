package com.example.lms.repository;

import com.example.lms.models.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn,Integer> {
       Txn findByTxnId(String txnId);
}
