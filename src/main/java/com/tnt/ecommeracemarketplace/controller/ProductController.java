package com.tnt.ecommeracemarketplace.controller;

import com.tnt.ecommeracemarketplace.dto.*;
import com.tnt.ecommeracemarketplace.facade.RedissonLockFacade;
import com.tnt.ecommeracemarketplace.service.OrderService;
import com.tnt.ecommeracemarketplace.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    private final OrderService orderService;

    private final RedissonLockFacade redissonLockFacade;

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

    @PostMapping("/orders")
    public ResponseEntity<ApiResponseDto> ordersSave(@RequestBody Map<String, Object> requestData) {

        Map<String, Object> product = (Map<String, Object>) requestData.get("product");

        Long productId = ((Integer) product.get("id")).longValue();
        Long quantity = ((Integer) requestData.get("quantity")).longValue();

        OrderRequestDto requestDto = new OrderRequestDto(productId,
                (String) product.get("title"), quantity);

        try {
            redissonLockFacade.buy(requestDto.getProductId(), requestDto.getQuantity());
//            orderService.saveOrders(requestDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponseDto("주문 완료", HttpStatus.ACCEPTED.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

//    @PostMapping("/ordersPessimistic")
//    public ResponseEntity<ApiResponseDto> ordersSavePessimistic(@RequestBody Map<String, Object> requestData) {
//
//        Map<String, Object> product = (Map<String, Object>) requestData.get("product");
//
//        Long productId = ((Integer) product.get("id")).longValue();
//        Long quantity = ((Integer) requestData.get("quantity")).longValue();
//
//        OrderRequestDto requestDto = new OrderRequestDto(productId,
//                (String) product.get("title"), quantity);
//
//        try {
//            productService.buyPessimistic(requestDto.getProductId(), requestDto.getQuantity());
//            orderService.saveOrders(requestDto);
//            return ResponseEntity.status(HttpStatus.ACCEPTED)
//                    .body(new ApiResponseDto("주문 완료", HttpStatus.ACCEPTED.value()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
//        }
//    }
}
