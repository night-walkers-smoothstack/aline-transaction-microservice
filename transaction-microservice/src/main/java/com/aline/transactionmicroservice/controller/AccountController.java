package com.aline.transactionmicroservice.controller;

import com.aline.transactionmicroservice.dto.TransactionResponse;
import com.aline.transactionmicroservice.model.Transaction;
import com.aline.transactionmicroservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final TransactionService service;

    @GetMapping(value = "/{id}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<TransactionResponse> getAllTransactionsByAccountId(@PathVariable long id, Pageable pageable) {
        Page<Transaction> transactionsPage = service.getAllTransactionsByAccountId(id, pageable);
        return transactionsPage.map(service::mapToResponse);
    }

}
