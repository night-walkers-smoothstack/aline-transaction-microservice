package com.aline.transactionmicroservice.model;

import com.aline.core.validation.annotation.AccountNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(TransactionMethod.Methods.ACH)
public class AchTransaction extends Transaction {

    @NotNull
    @AccountNumber
    private String accountNumber;

}
