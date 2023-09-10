package com.tnt.ecommeracemarketplace.controller;

import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.service.ProductEsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/person")
public class ProductEsController {

  private final ProductEsService productEsService;

  @GetMapping("/filtered")
  public ResponseEntity<ProductListResponseDto> searchProducts(
      @RequestParam(name = "page") int page,
      @RequestParam(name = "keyword") String keyword) {
    PageDto pageDto = PageDto.builder().currentPage(page - 1).build();
    ProductListResponseDto result = productEsService.findProducts(keyword, pageDto);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
