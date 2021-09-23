package com.aline.transactionmicroservice.service;

import com.aline.core.exception.notfound.AccountNotFoundException;
import com.aline.core.model.account.Account;
import com.aline.core.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    /**
     * Get an account by the account number
     * @param accountNumber The account number string
     * @return An account associated with the specified account number
     * @throws AccountNotFoundException when the account does not exist
     */
    public Account getAccountByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber).orElseThrow(AccountNotFoundException::new);
    }

}
