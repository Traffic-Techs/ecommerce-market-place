package com.tnt.ecommeracemarketplace.controller;

import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 제품 상세 조회 API
     * @param productId 조회할 제품
     */
    @GetMapping ("/products/{productId}")
    public ProductResponseDto productDetails (@PathVariable Long productId) {
        return productService.findProductDetails(productId);
    }
}
