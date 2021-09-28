package com.aline.transactionmicroservice.service;

import com.aline.core.annotation.test.SpringBootUnitTest;
import com.aline.core.model.account.Account;
import com.aline.transactionmicroservice.dto.CreateTransaction;
import com.aline.transactionmicroservice.model.Merchant;
import com.aline.transactionmicroservice.model.Transaction;
import com.aline.transactionmicroservice.model.TransactionMethod;
import com.aline.transactionmicroservice.model.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootUnitTest
@Sql(scripts = {"classpath:scripts/transactions.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class TransactionApiTest {

    @Autowired
    TransactionApi transactions;

    @Test
    void test_createTransaction_createsTransactionWithCorrectProperties_and_accountBalanceNotAffected_and_createsNewMerchant() {

        CreateTransaction createTransaction = CreateTransaction.builder()
                .amount(10000)
                .accountNumber("0011011234")
                .type(TransactionType.PURCHASE)
                .merchantCode("NEWM")
                .merchantName("New Merchant")
                .method(TransactionMethod.ACH)
                .build();

        Transaction transaction = transactions.createTransaction(createTransaction);
        Account account = transaction.getAccount();
        Merchant merchant = transaction.getMerchant();

        // Account balance not affected
        assertEquals(100000, account.getBalance());

        // Merchant and account information are correct
        assertEquals("0011011234", account.getAccountNumber());
        assertEquals("NEWM", merchant.getCode());
        assertEquals("New Merchant", merchant.getName());

        assertEquals(TransactionType.PURCHASE, transaction.getType());
        assertFalse(transaction.isIncreasing());
        assertTrue(transaction.isDecreasing());
        assertTrue(transaction.isMerchantTransaction());
    }

    @Test
    void test_createTransaction_createsTransactionWithCorrectProperties_for_withdrawal() {

        CreateTransaction createTransaction = CreateTransaction.builder()
                .amount(10000)
                .accountNumber("0011011234")
                .type(TransactionType.WITHDRAWAL)
                .method(TransactionMethod.ACH)
                .build();

        Transaction transaction = transactions.createTransaction(createTransaction);
        Account account = transaction.getAccount();

        // Account balance not affected
        assertEquals(100000, account.getBalance());

        // Merchant and account information are correct
        assertEquals("0011011234", account.getAccountNumber());
        assertNull(transaction.getMerchant());

        assertEquals(TransactionType.WITHDRAWAL, transaction.getType());
        assertFalse(transaction.isIncreasing());
        assertTrue(transaction.isDecreasing());
        assertFalse(transaction.isMerchantTransaction());
    }

}
