package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;

public interface ProductService {

  ProductListResponseDto getProducts();
  ProductListResponseDto selectProductList(String keyword);
}
