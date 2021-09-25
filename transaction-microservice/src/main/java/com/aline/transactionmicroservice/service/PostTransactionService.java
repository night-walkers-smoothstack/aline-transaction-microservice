package com.aline.transactionmicroservice.service;

import com.aline.core.exception.BadRequestException;
import com.aline.core.exception.UnprocessableException;
import com.aline.core.exception.notfound.AccountNotFoundException;
import com.aline.core.model.account.Account;
import com.aline.transactionmicroservice.dto.CreateTransaction;
import com.aline.transactionmicroservice.dto.Receipt;
import com.aline.transactionmicroservice.model.Merchant;
import com.aline.transactionmicroservice.model.Transaction;
import com.aline.transactionmicroservice.model.TransactionStatus;
import com.aline.transactionmicroservice.model.TransactionType;
import com.aline.transactionmicroservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final TransactionService service;
    private final AccountService accountService;
    private final MerchantService merchantService;
    private final TransactionRepository repository;
    private final ModelMapper mapper;

    @Transactional(rollbackOn = {
            AccountNotFoundException.class
    })
    public Transaction createTransaction(@Valid CreateTransaction createTransaction) {
        Transaction transaction = mapper.map(createTransaction, Transaction.class);

        if (createTransaction.getCardNumber() == null) {
            Account account = accountService.getAccountByAccountNumber(createTransaction.getAccountNumber());
            transaction.setAccount(account);
            transaction.setInitialBalance(account.getBalance());
        } else {
            throw new BadRequestException("Card services are currently unavailable. Please try again later.");
        }

        if (isMerchantTransaction(createTransaction.getType())) {
            Merchant merchant = merchantService.getMerchantByCode(createTransaction.getMerchantCode());
            transaction.setMerchant(merchant);
        }

        transaction.setStatus(TransactionStatus.PENDING); // Transactions will initially be pending when created
        transaction.setInitialized(true);
        return transaction;
    }

    /**
     * Boolean representing whether the transaction type
     * can be performed by a merchant.
     * @param type TransactionType enum
     * @return True if the transaction type can be performed by a merchant
     */
    public boolean isMerchantTransaction(TransactionType type) {
        switch (type) {
            case PURCHASE:
            case PAYMENT:
            case REFUND:
            case VOID:
            case DEPOSIT:
                return true;
            default:
                return false;
        }
    }

    /**
     * Processes an initialized transaction
     * @param transaction   An initialized transaction.
     *                      In order to initialize a transaction,
     *                      please see {@link #createTransaction(CreateTransaction)}.
     * @return A receipt of the processed transaction.
     */
    public Receipt processTransaction(Transaction transaction) {

        // Check that the transaction is initialized
        if (!transaction.isInitialized()) throw new UnprocessableException("Transaction is not initialized. Unable to process transaction.");



        return null;
    }

}
