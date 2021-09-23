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

    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    public Transaction getTransactionById(long id) {
        return repository.findById(id).orElseThrow(TransactionNotFoundException::new);
    }

    public Page<Transaction> getAllTransactionsByAccountNumber(@NonNull String accountNumber, @NonNull Pageable pageable) {
        return repository.findAllByAccount_AccountNumber(accountNumber, pageable);
    }
}
