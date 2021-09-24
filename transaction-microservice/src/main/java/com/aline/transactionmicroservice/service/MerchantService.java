package com.aline.transactionmicroservice.service;

import com.aline.transactionmicroservice.dto.CreateMerchant;
import com.aline.transactionmicroservice.exception.MerchantNotFoundException;
import com.aline.transactionmicroservice.model.Merchant;
import com.aline.transactionmicroservice.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

/**
 * Merchants are created either manually or dynamically.
 * If a merchants accepts funds from our API they will automatically
 * be added into our system as a merchant. They will not have populated
 * information such as description or qualifying name unless they
 * register their establishment under our API. However, the basic merchant
 * information will be saved in our database for reuse in the meantime.
 */
@Service
@RequiredArgsConstructor
public class MerchantService {

    private MerchantRepository repository;

    @PermitAll
    public Merchant getMerchantByCode(String code) {
        return repository.findById(code).orElseThrow(MerchantNotFoundException::new);
    }

    public Merchant autoCreateMerchant(@Valid CreateMerchant merchant) {
        // TODO: Implement create simple merchant
        return null;
    }

}
