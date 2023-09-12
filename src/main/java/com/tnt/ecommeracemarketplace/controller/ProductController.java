package com.tnt.ecommeracemarketplace.controller;

import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.facade.NamedLockFacade;
import com.tnt.ecommeracemarketplace.repository.ProductRepository;
import com.tnt.ecommeracemarketplace.service.OrderService;
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
  private final OrderService orderService;

//    private final RedissonLockFacade redissonLockFacade;

  private final ProductRepository productRepository;

  private final NamedLockFacade namedLockFacade;

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
}
