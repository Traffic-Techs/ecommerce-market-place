package com.tnt.ecommeracemarketplace.controller;

import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
//  @GetMapping("/products/details")
//  public ResponseEntity<ProductListResponseDto> searchProducts(
//      @RequestParam(name = "page") int page,
//      @RequestParam(name = "keyword") String keyword) {
//    PageDto pageDto = PageDto.builder().currentPage(page - 1).build();
//    ProductListResponseDto result = productService.selectProductList(keyword, pageDto);
//    return ResponseEntity.status(HttpStatus.OK).body(result);
//  }
//
//  @GetMapping("/products/filtered")
//  public ResponseEntity<ProductListResponseDto> searchProductsFilteredByCost(
//      @RequestParam(name = "page") int page,
//      @RequestParam("minCost") Long minCost,
//      @RequestParam("maxCost") Long maxCost) {
//    PageDto pageDto = PageDto.builder().currentPage(page - 1).build();
//    ProductListResponseDto result = productService.selectFilteredProduct(minCost, maxCost, pageDto);
//    return ResponseEntity.status(HttpStatus.OK).body(result);
//  }
}
