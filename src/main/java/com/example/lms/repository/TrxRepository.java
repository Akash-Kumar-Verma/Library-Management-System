package com.example.lms.repository;

import com.example.lms.models.Txn;
import com.example.lms.request.TxnCreateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrxRepository extends JpaRepository<Txn,Integer> {

}
