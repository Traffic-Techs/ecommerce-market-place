package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;

public interface ProductService {

    /**
     * 제품 전체 조회
     *
     * @return 조회할 제품 리스트 정보
     */
    ProductListResponseDto getProducts(PageDto pageDto);

    /**
     * 제품 단건 조회
     *
     * @param productId 조회할 제품 아이디
     * @return 조회할 제품 정보
     */
    ProductResponseDto findProductDetails(Long productId);

    /**
     * 제품 검색
     *
     * @param keyword 검색어
     * @return 조회할 제품 리스트 정보
     */
    ProductListResponseDto selectProductList(String keyword, PageDto pageDto);

    /**
     * 제품 구매
     * @param id 구매할 제품 아이디
     * @param quantity 구매할 수량
     */
    void buyProduct(Long id, Long quantity);

    /**
     * 낙관적락 적용한 제품 구매
     * @param id
     * @param quantity
     */
    void buyPessimistic (Long id, Long quantity);
}
