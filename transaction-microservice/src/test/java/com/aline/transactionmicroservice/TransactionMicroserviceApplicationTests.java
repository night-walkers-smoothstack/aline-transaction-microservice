package com.aline.transactionmicroservice;

import com.aline.core.annotation.test.SpringBootIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootIntegrationTest
class TransactionMicroserviceApplicationTests {

    @Autowired
    TransactionMicroserviceApplication application;

    @Test
    void contextLoads() {
        assertNotNull(application);
    }

}
