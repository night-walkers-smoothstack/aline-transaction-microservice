package com.aline.transactionmicroservice.repository;

import com.aline.core.repository.JpaRepositoryWithSpecification;
import com.aline.transactionmicroservice.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepositoryWithSpecification<Transaction, Long> {

    Page<Transaction> findAllByAccount_AccountNumber(String accountNumber, Pageable pageable);

}
