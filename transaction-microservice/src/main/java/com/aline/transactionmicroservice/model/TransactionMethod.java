package com.aline.transactionmicroservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Transaction methods
 */
@RequiredArgsConstructor
public enum TransactionMethod {
    ACH(Methods.ACH),
    ATM(Methods.ATM),
    CARD(Methods.CARD);

    @Getter
    private final String method;

    public static class Methods {
        public static final String ACH = "ACH";
        public static final String ATM = "ATM";
        public static final String CARD = "CARD";
    }
}
