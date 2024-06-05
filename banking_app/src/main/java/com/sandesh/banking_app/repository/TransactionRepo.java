package com.sandesh.banking_app.repository;

import com.sandesh.banking_app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccIdOrderByTimestampDesc(Long accId);
}
