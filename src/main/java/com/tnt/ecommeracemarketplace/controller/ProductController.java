package com.tnt.ecommeracemarketplace.controller;

import com.tnt.ecommeracemarketplace.dto.ApiResponseDto;
import com.tnt.ecommeracemarketplace.dto.OrderRequestDto;
import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.entity.Users;
import com.tnt.ecommeracemarketplace.repository.ProductRepository;
import com.tnt.ecommeracemarketplace.security.UserDetailsImpl;
import com.tnt.ecommeracemarketplace.service.OrderService;
import com.tnt.ecommeracemarketplace.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

  private final ProductServiceImpl productService;

  private final OrderService orderService;

  private final ProductRepository productRepository;


  /**
   * 제품 전체 조회 API
   */
  @GetMapping("/products")
  public ResponseEntity<ProductListResponseDto> getProducts(
      @RequestParam(name = "page", required = false) Integer page) {
    PageDto pageDto = PageDto.builder().currentPage(page).build();
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


  // 상품 구매
  @PostMapping("/orders")
  public ResponseEntity<ApiResponseDto> ordersSave(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody Map<String, Object> requestData) {


        Map<String, Object> product = (Map<String, Object>) requestData.get("product");

        Long productId = ((Integer) product.get("id")).longValue();
        Long quantity = ((Integer) requestData.get("quantity")).longValue();
//        Long productId = 1L;
//        Long quantity = 1L;
//    Long productId = requestDto.getProductId();
//    Long quantity = requestDto.getQuantity();

    productService.orderProducts(productId, quantity, userDetails.getUser());

    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(new ApiResponseDto("주문 완료", HttpStatus.ACCEPTED.value()));
  }
}
