package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;

public interface ProductService {

    /**
     * 제품 단건 조회
     * @param productId 조회할 제품 아이디
     * @return 조회할 제품 정보
     */
    ProductResponseDto findProductDetails(Long productId);
}
