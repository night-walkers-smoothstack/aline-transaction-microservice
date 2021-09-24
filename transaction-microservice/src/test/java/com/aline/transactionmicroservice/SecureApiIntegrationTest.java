package com.aline.transactionmicroservice;

import com.aline.core.annotation.test.SpringBootIntegrationTest;
import com.aline.core.annotation.test.SpringTestProperties;
import com.aline.core.model.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootIntegrationTest
@Sql(scripts = "classpath:scripts/transactions.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
class SecureApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test_boy", authorities = {"MEMBER"})
    void test_getTransactionById_statusIsOk_if_userOwnsResource() throws Exception {

        mockMvc.perform(get("/transactions/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

    }

}
