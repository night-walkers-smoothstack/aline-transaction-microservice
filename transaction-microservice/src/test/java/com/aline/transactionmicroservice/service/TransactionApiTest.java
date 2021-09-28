package com.aline.transactionmicroservice.service;

import com.aline.core.annotation.test.SpringBootUnitTest;
import com.aline.core.exception.UnprocessableException;
import com.aline.core.model.account.Account;
import com.aline.transactionmicroservice.dto.CreateTransaction;
import com.aline.transactionmicroservice.model.Merchant;
import com.aline.transactionmicroservice.model.Transaction;
import com.aline.transactionmicroservice.model.TransactionMethod;
import com.aline.transactionmicroservice.model.TransactionState;
import com.aline.transactionmicroservice.model.TransactionType;
import com.aline.transactionmicroservice.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootUnitTest
@Sql(scripts = {"classpath:scripts/transactions.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class TransactionApiTest {

    @Autowired
    TransactionApi transactions;

    @Autowired
    TransactionRepository repository;

    @Nested
    @DisplayName("Test CREATED state transaction")
    class CreateTransactionsTest {

        @Test
        void test_createsTransactionWithCorrectProperties_and_accountBalanceNotAffected_and_createsNewMerchant() {

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
        void test_createsTransactionWithCorrectProperties_for_withdrawal() {

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

        @Test
        void test_createsTransactionWithCorrectProperties_with_existingMerchant() {

            CreateTransaction createTransaction = CreateTransaction.builder()
                    .amount(10000)
                    .accountNumber("0011011234")
                    .type(TransactionType.PURCHASE)
                    .merchantCode("ALNE")
                    .merchantName("Aline Financial Online Store")
                    .method(TransactionMethod.ACH)
                    .build();

            Transaction transaction = transactions.createTransaction(createTransaction);
            Account account = transaction.getAccount();
            Merchant merchant = transaction.getMerchant();

            // Account balance not affected
            assertEquals(100000, account.getBalance());

            // Merchant and account information are correct
            assertEquals("0011011234", account.getAccountNumber());
            assertEquals("ALNE", merchant.getCode());
            assertNotEquals(createTransaction.getMerchantName(), merchant.getName()); // If Merchant exists use existing name
            assertEquals("Aline Financial Bank", merchant.getName());

            assertEquals(TransactionType.PURCHASE, transaction.getType());
            assertFalse(transaction.isIncreasing());
            assertTrue(transaction.isDecreasing());
            assertTrue(transaction.isMerchantTransaction());

        }

        @Test
        void test_canDelete() {
            CreateTransaction createTransaction = CreateTransaction.builder()
                    .amount(10000)
                    .accountNumber("0011011234")
                    .type(TransactionType.PURCHASE)
                    .merchantCode("ALNE")
                    .merchantName("Aline Financial Online Store")
                    .method(TransactionMethod.ACH)
                    .build();

            Transaction transaction = transactions.createTransaction(createTransaction);
            Account account = transaction.getAccount();
            Merchant merchant = transaction.getMerchant();

            // Account balance not affected
            assertEquals(100000, account.getBalance());

            // Merchant and account information are correct
            assertEquals("0011011234", account.getAccountNumber());
            assertEquals("ALNE", merchant.getCode());
            assertNotEquals(createTransaction.getMerchantName(), merchant.getName()); // If Merchant exists use existing name
            assertEquals("Aline Financial Bank", merchant.getName());

            assertEquals(TransactionType.PURCHASE, transaction.getType());
            assertFalse(transaction.isIncreasing());
            assertTrue(transaction.isDecreasing());
            assertTrue(transaction.isMerchantTransaction());

            assertDoesNotThrow(() -> transactions.deleteTransactionById(transaction.getId()));

            assertNull(repository.findById(transaction.getId()).orElse(null));
        }
    }

    @Nested
    @DisplayName("Test PROCESSING state transaction")
    class ProcessingTransactionsTest {

        @Test
        void test_unprocessableWhenTransactionNotCreated() {
            Transaction transaction = new Transaction();
            assertThrows(UnprocessableException.class, () -> transactions.processTransaction(transaction));
        }

        @Test
        void test_unprocessableWhenTransactionPosted() {
            Transaction transaction = new Transaction();
            transaction.setState(TransactionState.POSTED);
            assertThrows(UnprocessableException.class, () -> transactions.processTransaction(transaction));
        }

    }

}
