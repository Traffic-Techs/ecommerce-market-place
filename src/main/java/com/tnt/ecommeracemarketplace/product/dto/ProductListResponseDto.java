package com.tnt.ecommeracemarketplace.product.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ProductListResponseDto {
  private List<ProductResponseDto> productList;
  public ProductListResponseDto(List<ProductResponseDto> productList) {
    this.productList = productList;
  }
}
