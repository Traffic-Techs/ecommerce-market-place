package com.tnt.ecommeracemarketplace.product.service;

import com.tnt.ecommeracemarketplace.product.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.product.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.product.entity.Products;
import com.tnt.ecommeracemarketplace.product.repository.repository.ProductRepository;
import com.tnt.ecommeracemarketplace.product.repository.repository.ProductSearchCond;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto findProductDetails(Long productId) {

        Products productToFind = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 제품이 존재하지 않습니다")
        );

        return new ProductResponseDto(productToFind);
    }

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
