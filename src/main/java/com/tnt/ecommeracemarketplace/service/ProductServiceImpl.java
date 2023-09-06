package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.entity.Products;
import com.tnt.ecommeracemarketplace.repository.ProductRepository;
import com.tnt.ecommeracemarketplace.repository.ProductSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto findProductDetails(Long productId) {

        Products productToFind = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 제품이 존재하지 않습니다")
        );

        System.out.println(productToFind.getTitle());

        return new ProductResponseDto(productToFind);
    }

    @Override
    public ProductListResponseDto getProducts(PageDto pageDto) {
        Page<Products> productPage = productRepository.findAll(pageDto.toPageable());

        List<ProductResponseDto> productList = productPage.getContent().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());

        return new ProductListResponseDto(productList);
    }

    // Spring Data 검색
//      @Override
//      public ProductListResponseDto selectProductList(String keyword) {
//        List<ProductResponseDto> productList = productRepository.findByTitleContainingIgnoreCase(keyword).stream()
//            .map(ProductResponseDto::new).collect(Collectors.toList());
//        return new ProductListResponseDto(productList);
//      }

    // JPAQueryFactory 검색
    @Override
    public ProductListResponseDto selectProductList(String keyword, PageDto pageDto) {
        var cond = ProductSearchCond.builder().keyword(keyword).build();

        Pageable pageable = pageDto.toPageable();
        Page<Products> productPage = productRepository.search(cond, pageable);

        List<ProductResponseDto> productList = productPage.getContent().stream()
                .map(ProductResponseDto::new).collect(Collectors.toList());

        return new ProductListResponseDto(productList);
    }

}
