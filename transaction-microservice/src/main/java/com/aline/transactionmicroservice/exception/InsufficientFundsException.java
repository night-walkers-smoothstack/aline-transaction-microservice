package com.aline.transactionmicroservice.exception;

import com.aline.core.exception.BadRequestException;

public class InsufficientFundsException extends BadRequestException {
    public InsufficientFundsException() {
        super("Unable to complete transaction due to insufficient funds.");
    }
}
