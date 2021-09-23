package com.aline.transactionmicroservice;

import com.aline.core.annotation.test.SpringBootIntegrationTest;
import com.aline.transactionmicroservice.exception.TransactionNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootIntegrationTest
@Sql(scripts = "classpath:scripts/transactions.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class TransactionApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_getTransactionById_status_isOk_when_transactionExists() throws Exception {
        mockMvc.perform(get("/transactions/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountNumber").value("******1234"));
    }

    @Test
    void test_getTransactionById_status_isNotFound_when_transactionDoesNotExist() throws Exception {
        mockMvc.perform(get("/transactions/{id}", 999))
                .andExpect(status().isNotFound())
                .andExpect(content().string((new TransactionNotFoundException()).getMessage()));
    }

    @Test
    void test_getAllTransactionsByAccountNumber_status_isOk() throws Exception {
        mockMvc.perform(get("/accounts/account-number/{accountNumber}/transactions",
                        "0011011234"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(2))
                .andDo(print());
    }

    @Test
    void test_getAllTransactionsByAccountId_status_isOk() throws Exception {
        mockMvc.perform(get("/accounts/{id}/transactions", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(2))
                .andDo(print());
    }

}
