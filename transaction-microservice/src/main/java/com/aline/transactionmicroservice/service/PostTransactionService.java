package com.aline.transactionmicroservice.service;

import com.aline.core.exception.BadRequestException;
import com.aline.core.exception.UnprocessableException;
import com.aline.core.exception.notfound.AccountNotFoundException;
import com.aline.core.model.account.Account;
import com.aline.core.model.account.AccountType;
import com.aline.core.model.account.CheckingAccount;
import com.aline.transactionmicroservice.dto.CreateTransaction;
import com.aline.transactionmicroservice.dto.MerchantResponse;
import com.aline.transactionmicroservice.dto.Receipt;
import com.aline.transactionmicroservice.model.Merchant;
import com.aline.transactionmicroservice.model.Transaction;
import com.aline.transactionmicroservice.model.TransactionStatus;
import com.aline.transactionmicroservice.model.TransactionType;
import com.aline.transactionmicroservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
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
            Merchant merchant = merchantService.checkMerchant(
                    createTransaction.getMerchantCode(),
                    createTransaction.getMerchantName());
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

        // Perform the passed transaction
        performTransaction(transaction);

        val receipt = mapper.map(transaction, Receipt.class);

        if (transaction.isMerchantTransaction()) {
            receipt.setMerchantResponse(mapper.map(
                    transaction.getMerchant(),
                    MerchantResponse.class));
        }

        return receipt;
    }

    /**
     * Approve the transaction
     * @param transaction The transaction to approve
     * @return A receipt of the approved transaction
     */
    public Receipt approveTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatus.APPROVED);
        return processTransaction(transaction);
    }

    /**
     * Deny the transaction
     * @param transaction The transaction to deny
     * @return A receipt of the denied transaction
     */
    public Receipt denyTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatus.DENIED);
        return processTransaction(transaction);
    }

    /**
     * Apply transaction increase or decrease balance to the
     * account attached to the transaction. The transaction will
     * only be performed if it has been approved.
     * <br>
     * If status is pending and the account is a checking account
     * then the available balance will be updated.
     * @param transaction The transaction to perform
     */
    public void performTransaction(Transaction transaction) {
        boolean isIncreasing = transaction.isIncreasing();
        boolean isDecreasing = transaction.isDecreasing();
        int amount = transaction.getAmount();

        Account account = transaction.getAccount();

        // If transaction is approved, decrease actual balance
        if (transaction.getStatus() == TransactionStatus.APPROVED) {
            if (isIncreasing && !isDecreasing) {
                account.increaseBalance(amount);
            } else if (isDecreasing && !isIncreasing) {
                account.decreaseBalance(amount);
            }

        } else if (transaction.getStatus() == TransactionStatus.PENDING) {

            // If transaction is pending and account is checking, decrease available balance
            if (account.getAccountType() == AccountType.CHECKING) {
                val checkingAccount = (CheckingAccount) account;
                if (isIncreasing && !isDecreasing) {
                    checkingAccount.increaseAvailableBalance(amount);
                } else if (isDecreasing && !isIncreasing) {
                    checkingAccount.decreaseAvailableBalance(amount);
                }
                transaction.setPostedBalance(checkingAccount.getAvailableBalance());
            }
        }

        transaction.setPostedBalance(account.getBalance());

        // Save transaction
        repository.save(transaction);
    }

}
