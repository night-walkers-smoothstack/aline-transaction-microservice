package com.aline.transactionmicroservice;

import com.aline.core.annotation.EnableCoreModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCoreModule
@OpenAPIDefinition(info =
    @Info(
            title = "Transaction Microservice",
            description = "Handle all account transaction",
            version = "1.0"
    )
)
public class TransactionMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionMicroserviceApplication.class, args);
    }

}
