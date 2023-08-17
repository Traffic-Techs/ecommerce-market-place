package com.tnt.ecommeracemarketplace.controller;

import com.tnt.ecommeracemarketplace.dto.PageDTO;
import com.tnt.ecommeracemarketplace.entity.Product;
import com.tnt.ecommeracemarketplace.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
  public Page<Product> searchProducts(@RequestParam(name = "keyword") String keyword, PageDTO pageDTO) {
    return (Page<Product>) productService.selectProductList(keyword, pageDTO);
  }
}
