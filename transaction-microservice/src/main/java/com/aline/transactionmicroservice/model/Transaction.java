package com.aline.transactionmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
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
     * Transaction amount
     * <br>
     * <em>Represented in cents. (Integer instead of float)</em>
     */
    @NotNull
    @PositiveOrZero
    private Integer amount;

    /**
     * The date the transaction was made
     */
    @CreationTimestamp
    private Date date;

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
