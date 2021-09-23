package com.aline.transactionmicroservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Transaction methods
 */
@RequiredArgsConstructor
public enum TransactionMethod {
    ACH,
    ATM,
    CARD
}
