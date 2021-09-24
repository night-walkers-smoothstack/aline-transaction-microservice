package com.aline.transactionmicroservice.service;

import com.aline.transactionmicroservice.dto.CreateTransaction;
import com.aline.transactionmicroservice.dto.Receipt;
import com.aline.transactionmicroservice.model.Transaction;
import com.aline.transactionmicroservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * Post transaction service handles the processing
 * and posting of transactions created by users
 * using the public Transaction API. The transaction API can
 * only be accessed by authorized users whether that be
 * members, vendors, or other authorized individuals. They
 * will be provided an API key. They will register as a vendor
 * with the bank first in order to accept payment from our cards
 * and checks. Afterwards, their systems should be able to access
 * our services to create transactions and receive funds.
 */
@Service
@RequiredArgsConstructor
public class PostTransactionService {
    private final TransactionRepository repository;
    private final ModelMapper mapper;

    public Receipt createTransaction(@Valid CreateTransaction createTransaction) {
        Transaction transaction = mapper.map(createTransaction, Transaction.class);
        return null;
    }

}
