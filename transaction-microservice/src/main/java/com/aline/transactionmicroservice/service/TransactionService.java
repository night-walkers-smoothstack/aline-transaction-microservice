package com.aline.transactionmicroservice.service;

import com.aline.transactionmicroservice.exception.TransactionNotFoundException;
import com.aline.transactionmicroservice.model.Transaction;
import com.aline.transactionmicroservice.repository.TransactionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;

    /**
     * Save a transaction into the database
     * <br>
     * <strong>
     *     * This does not affect any accounts associated with it.
     *      The transaction will not be processed.
     * </strong>
     * @param transaction The transaction to save
     * @return The saved transaction
     */
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    /**
     * Retrieve a transaction by its ID
     * @param id The ID of the transaction
     * @return The retrieved transaction
     * @throws TransactionNotFoundException if the transaction does not exist
     */
    public Transaction getTransactionById(long id) {
        return repository.findById(id).orElseThrow(TransactionNotFoundException::new);
    }

    public Page<Transaction> getAllTransactionsByAccountNumber(@NonNull String accountNumber, @NonNull Pageable pageable) {
        return repository.findAllByAccount_AccountNumber(accountNumber, pageable);
    }
}
