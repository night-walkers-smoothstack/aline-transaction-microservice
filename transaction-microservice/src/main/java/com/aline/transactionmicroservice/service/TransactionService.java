package com.aline.transactionmicroservice.service;

import com.aline.core.model.account.Account;
import com.aline.transactionmicroservice.dto.TransactionResponse;
import com.aline.transactionmicroservice.exception.TransactionNotFoundException;
import com.aline.transactionmicroservice.model.Transaction;
import com.aline.transactionmicroservice.repository.TransactionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final AccountService accountService;
    private final ModelMapper modelMapper;

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

    /**
     * Get all transactions by the account number
     * @param accountNumber Account number string
     * @param pageable The pageable object passed in by the controller
     * @return A page of transactions
     * @see AccountService#getAccountByAccountNumber(String)
     */
    public Page<Transaction> getAllTransactionsByAccountNumber(@NonNull String accountNumber, @NonNull Pageable pageable) {
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        return getAllTransactionsByAccount(account, pageable);
    }

    /**
     * Get all transaction pages by account ID
     * @param id The ID of the account being queried for transactions
     * @param pageable Pageable object passed in by the controller
     * @return A page of transactions based on the account ID
     */
    public Page<Transaction> getAllTransactionsByAccountId(long id, @NonNull Pageable pageable) {
        Account account = accountService.getAccountById(id);
        return getAllTransactionsByAccount(account, pageable);
    }

    /**
     * Get all transactions associated with an account entity
     * @param account The account entity
     * @param pageable The pageable object passed in by the calling controller
     * @return A page of transacitons
     */
    public Page<Transaction> getAllTransactionsByAccount(@NonNull Account account, @NonNull Pageable pageable) {
        return repository.findAllByAccount(account, pageable);
    }

    /**
     * Map a transaction entity to it's DTO type TransactionResponse,
     * @param transaction The transaction to map
     * @return The transaction response DTO
     * @apiNote A TransactionResponse should always be the preferred
     *          response DTO for a transaction information request.
     */
    public TransactionResponse mapToResponse(Transaction transaction) {
        String accountNumber = transaction.getAccount().getAccountNumber();
        TransactionResponse response = modelMapper.map(transaction, TransactionResponse.class);
        response.setAccountNumber(accountService.maskAccountNumber(accountNumber));
        return response;
    }

}
