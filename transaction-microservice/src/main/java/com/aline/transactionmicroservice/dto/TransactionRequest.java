package com.aline.transactionmicroservice.dto;

import com.aline.transactionmicroservice.model.TransactionMethod;
import com.aline.transactionmicroservice.model.TransactionStatus;
import com.aline.transactionmicroservice.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create a TransactionRequest to make a transaction
 * to a specified account with the specified amount.
 *
 * This DTO is used to manually create a transaction.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private TransactionType type;
    private TransactionMethod method;
    private int amount;
    private TransactionStatus status;
    private String merchantCode;
    private String merchantName;

}
