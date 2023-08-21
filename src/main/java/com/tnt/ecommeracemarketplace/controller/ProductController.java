package com.tnt.ecommeracemarketplace.controller;

import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    /**
     * 제품 전체 조회 API
     */
    @GetMapping("/products")
    public ResponseEntity<ProductListResponseDto> getProducts(@RequestParam("page") int page) {
        PageDto pageDto = PageDto.builder().currentPage(page - 1).build();
        ProductListResponseDto result = productService.getProducts(pageDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 제품 상세 조회 API
     *
     * @param productId 조회할 제품
     */
    @GetMapping("/products/{productId}")
    public ProductResponseDto productDetails(@PathVariable Long productId) {
        return productService.findProductDetails(productId);
    }

    /**
     * 제품 검색 API
     *
     * @param keyword 검색어
     */
    @GetMapping("/products/details")
    public ProductListResponseDto searchProducts(@RequestParam(name = "page") int page,
                                                 @RequestParam(name = "keyword") String keyword) {
        PageDto pageDto = PageDto.builder().currentPage(page - 1).build();
        return productService.selectProductList(keyword, pageDto);
    }
}
