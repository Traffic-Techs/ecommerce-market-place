package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.entity.Products;
import com.tnt.ecommeracemarketplace.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "product", key = "#productId")
    @Override
    public ProductResponseDto findProductDetails(Long productId) {

        log.info("ID {}에 대한 캐시된 제품 검색", productId);

        Products productToFind = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 제품이 존재하지 않습니다")
        );

        log.info("ID {}에 대한 데이터베이스에서 제품 검색", productId);

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
    @Override
    public ProductListResponseDto selectProductList(String keyword) {
      List<ProductResponseDto> productList = productRepository.findByTitleContainingIgnoreCase(keyword).stream()
          .map(ProductResponseDto::new).collect(Collectors.toList());
      return new ProductListResponseDto(productList);
    }

    // JPAQueryFactory 검색
//    @Override
//    public ProductListResponseDto selectProductList(String keyword) {
//      var cond = ProductSearchCond.builder().keyword(keyword).build();
//      List<ProductResponseDto> productList = productRepository.search(cond).stream()
//          .map(ProductResponseDto::new).collect(Collectors.toList());
//      return new ProductListResponseDto(productList);
//    }

}
