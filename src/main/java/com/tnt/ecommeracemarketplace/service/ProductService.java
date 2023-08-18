package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;

public interface ProductService {

  /**
   * 제품 전체 조회
   * @return 조회할 제품 리스트 정보
   */
  ProductListResponseDto getProducts();

  /**
   * 제품 단건 조회
   * @param productId 조회할 제품 아이디
   * @return 조회할 제품 정보
   */
  ProductResponseDto findProductDetails(Long productId);


  /**
   * 제품 검색
   * @param keyword 검색어
   * @return 조회할 제품 리스트 정보
   */
  ProductListResponseDto selectProductList(String keyword);
}
