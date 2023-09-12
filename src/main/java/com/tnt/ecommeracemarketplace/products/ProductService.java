package com.tnt.ecommeracemarketplace.products;

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

//  ProductListResponseDto selectProductList(String keyword, PageDto pageDto);

//  ProductListResponseDto selectFilteredProduct(Long minCost, Long maxCost, PageDto pageDto);
}
