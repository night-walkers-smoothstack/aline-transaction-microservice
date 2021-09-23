package com.aline.transactionmicroservice.model;

import com.aline.core.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(TransactionMethod.Methods.ACH)
public class AchTransaction extends Transaction {

    /**
     * The account to apply the transaction to
     */
    @NotNull
    @ManyToOne
    private Account account;

}
