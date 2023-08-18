package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.repository.ProductRepository;
import com.tnt.ecommeracemarketplace.repository.ProductSearchCond;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

  private final ProductRepository productRepository;

  @Override
  public ProductListResponseDto getProducts() {
    List<ProductResponseDto> productList = productRepository.findAll().stream()
        .map(ProductResponseDto::new)
        .collect(Collectors.toList());

    return new ProductListResponseDto(productList);
  }

  // Spring Data 검색
//  @Override
//  public ProductListResponseDto selectProductList(String keyword) {
//    List<ProductResponseDto> productList = productRepository.findByTitleContainingIgnoreCase(keyword).stream()
//        .map(ProductResponseDto::new).collect(Collectors.toList());
//    return new ProductListResponseDto(productList);
//  }

  // JPAQueryFactory 검색
  @Override
  public ProductListResponseDto selectProductList(String keyword) {
    var cond = ProductSearchCond.builder().keyword(keyword).build();
    List<ProductResponseDto> productList = productRepository.search(cond).stream()
        .map(ProductResponseDto::new).collect(Collectors.toList());
    return new ProductListResponseDto(productList);
  }

}
