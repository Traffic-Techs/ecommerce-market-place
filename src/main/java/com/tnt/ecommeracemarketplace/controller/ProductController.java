package com.tnt.ecommeracemarketplace.controller;

import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

  private final ProductServiceImpl productService;

  @GetMapping("/products")
  public ResponseEntity<ProductListResponseDto> getProducts() {
    ProductListResponseDto result = productService.getProducts();
    return ResponseEntity.status(200).body(result);
  }

  @GetMapping("/products/details")
  public ProductListResponseDto searchProducts(@RequestParam(name = "keyword") String keyword) {
    return productService.selectProductList(keyword);
  }
}
