package com.aline.transactionmicroservice.repository;

import com.aline.core.model.account.Account;
import com.aline.core.repository.JpaRepositoryWithSpecification;
import com.aline.transactionmicroservice.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Transaction repository
 */
@Repository
public interface TransactionRepository extends JpaRepositoryWithSpecification<Transaction, Long> {

    /**
     * Find all transactions by the account
     * @param account The account that is associated with all the transactions
     * @param pageable Pageable object for a page response
     * @return A transactions page
     */
    Page<Transaction> findAllByAccount(Account account, Pageable pageable);

}
