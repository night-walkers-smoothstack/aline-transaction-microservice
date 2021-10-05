package com.aline.transactionmicroservice.dto;

import com.aline.core.validation.annotation.AccountNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferFundsRequest {

    @NotBlank
    @AccountNumber
    private String fromAccountNumber;
    @NotNull
    @AccountNumber
    private String toAccountNumber;
    @NotNull
    @PositiveOrZero
    private int amount;

}
