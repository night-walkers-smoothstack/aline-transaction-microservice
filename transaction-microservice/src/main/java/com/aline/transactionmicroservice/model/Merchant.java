package com.aline.transactionmicroservice.model;

import com.aline.core.validation.annotation.Address;
import com.aline.core.validation.annotation.Zipcode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * A merchant is an entity that receives or
 * awards funds from or to a member's account
 * either through direct deposit, ACH, or checks.
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {

    /**
     * The merchant code used to identify a merchant
     */
    @Id
    @Length(min = 4, max = 4)
    private String code;

    /**
     * Full qualified name of a merchant
     */
    @NotNull
    @Length(max = 150)
    private String name;

    /**
     * Optional description of the merchant.
     */
    @Length(max = 255)
    private String description;

    /**
     * Merchant's address
     */
    @Address
    private String address;

    private String city;

    private String state;

    @Zipcode
    private String zipcode;

    @CreationTimestamp
    private LocalDateTime registeredAt;

}
