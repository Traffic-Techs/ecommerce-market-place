package com.tnt.ecommeracemarketplace.products;

import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  // 전체 조회
  @Override
  public ProductListResponseDto getProducts(PageDto pageDto) {
    Pageable pageable = pageDto.toPageable();
    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    Page<Products> productPage = productRepository.findAll(pageable);

    List<ProductResponseDto> productList = productPage.getContent().stream()
        .map(ProductResponseDto::new)
        .collect(Collectors.toList());

    return new ProductListResponseDto(productList);
  }

  @Override
  public ProductResponseDto findProductDetails(Long productId) {
    Products productToFind = productRepository.findById(productId).orElseThrow(
        () -> new NullPointerException("해당 제품이 존재하지 않습니다")
    );
    return new ProductResponseDto(productToFind);
  }
}
