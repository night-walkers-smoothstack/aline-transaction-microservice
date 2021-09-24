package com.aline.transactionmicroservice.service;

import com.aline.transactionmicroservice.dto.CreateMerchant;
import com.aline.transactionmicroservice.exception.MerchantNotFoundException;
import com.aline.transactionmicroservice.model.Merchant;
import com.aline.transactionmicroservice.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private ModelMapper mapper;

    /**
     * Get Merchant by merchant code
     * @param code The merchant code
     * @return A Merchant with the specified code
     */
    @PermitAll
    public Merchant getMerchantByCode(String code) {
        return repository.findById(code).orElseThrow(MerchantNotFoundException::new);
    }

    /**
     * Register a merchant
     * @param createMerchant    The CreateMerchant DTO is used to save
     *                          a merchant in our database for reuse.
     * @return The saved Merchant entity
     */
    public Merchant registerMerchant(@Valid CreateMerchant createMerchant) {
        Merchant merchant = mapper.map(createMerchant, Merchant.class);
        return repository.save(merchant);
    }

}
