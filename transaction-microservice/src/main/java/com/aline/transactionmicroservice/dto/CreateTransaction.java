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
public class CreateTransaction {

    /**
     * The type of transaction.
     * Specifies whether it was a purchase, payment,
     * refund, etc...
     */
    private TransactionType type;

    /**
     * The method the transaction used whether
     * it was through a credit card or ACH.
     */
    private TransactionMethod method;

    /**
     * The amount of the transaction in cents.
     */
    private int amount;

    /**
     * The initial status of the transaction.
     */
    private TransactionStatus status;

    /**
     * The merchant this transaction was
     * made to (This is only required if the
     * transaction was a purchase, deposit, refund,
     * payment, or void).
     * @see TransactionType
     */
    private String merchantCode;

    /**
     * Description of the transaction
     */
    private String description;

    /**
     * Card number is required if account number
     * is not specified.
     */
    private String cardNumber;

    /**
     * Account number is required if card number
     * is not specified.
     */
    private String accountNumber;

}
