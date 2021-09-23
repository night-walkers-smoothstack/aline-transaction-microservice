package com.aline.transactionmicroservice.model;

import com.aline.core.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Transactions affect the balance of the associated
 * account.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    /**
     * Transaction ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The transaction method.
     * <br>
     * <em>How the transaction was processed</em>
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionMethod method;

    /**
     * Transaction amount
     * <br>
     * <em>Represented in cents. (Integer instead of float)</em>
     */
    @NotNull
    @PositiveOrZero
    private Integer amount;

    /**
     * The account the transaction is being
     * applied to. (This is very important to include
     * in the DTO)
     */
    @NotNull
    @ManyToOne
    private Account account;

    /**
     * The balance of the account at the time
     * of the transaction <em>(before anything is applied
     * to the account)</em>
     */
    @NotNull
    private Integer initialBalance;

    /**
     * The balance actually posted to the account after
     * the transaction is applied
     */
    @NotNull
    private Integer postedBalance;

    /**
     * Transaction type specifies whether it
     * was a purchase, payment, refund, etc...
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    /**
     * Transaction status (ACTIVE, PENDING, DENIED)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    /**
     * The date the transaction was made
     */
    @CreationTimestamp
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id) && amount.equals(that.amount) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, date);
    }
}
