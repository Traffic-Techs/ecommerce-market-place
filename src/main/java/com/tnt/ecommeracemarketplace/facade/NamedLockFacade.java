package com.tnt.ecommeracemarketplace.facade;

import com.tnt.ecommeracemarketplace.repository.NamedLockRepository;
import com.tnt.ecommeracemarketplace.service.ProductServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class NamedLockFacade {
    private final NamedLockRepository namedLockRepository;

    private final ProductServiceImpl productService;

    public NamedLockFacade(NamedLockRepository namedLockRepository, ProductServiceImpl productService) {
        this.namedLockRepository = namedLockRepository;
        this.productService = productService;
    }

    public void decrease (Long id, Long quantity) {
        try {
            namedLockRepository.getLock(id.toString());
            productService.buyProduct(id, quantity);
        } finally {
            namedLockRepository.releaseLock(id.toString());
        }
    }
}
