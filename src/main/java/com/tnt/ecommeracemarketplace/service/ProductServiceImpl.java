package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.ApiResponseDto;
import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.entity.Orders;
import com.tnt.ecommeracemarketplace.entity.Products;
import com.tnt.ecommeracemarketplace.error.CustomException;
import com.tnt.ecommeracemarketplace.repository.OrderRepository;
import com.tnt.ecommeracemarketplace.repository.ProductRepository;
import com.tnt.ecommeracemarketplace.repository.ProductSearchCond;
import jakarta.persistence.LockModeType;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

//    private final RedissonClient redissonClient;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

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

    // 상품 주문
    @Transactional
    public void orderProduct(Long id, Long quantity) {

        if(quantity < 1) throw new IllegalArgumentException("재고 부족");

//        Products productTest = productRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("재고 부족")
//        );

        Products product = productModify(id, quantity);

        // 주문 데이터 저장
        Orders order = new Orders();
        order.setAmount(quantity);
        order.setProduct_price(product.getCost());
        order.setProducts(product);
        order.setOrder_date(new Date());
        order.setTotal_price(product.getCost() * quantity);

        orderRepository.save(order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Products productModify(Long id, Long quantity){

        Products product = productRepository.findByIdWithPessimisticLock(id);

        logger.info("Current amount (visible to this thread): {}", product.getAmount());

        // 재고 부족 예외처리
        if(product.getAmount() < quantity) throw new IllegalArgumentException("재고 부족");

        // 상품 재고 차감
        product.buy(quantity);

        productRepository.saveAndFlush(product);

        logger.info("buyPessimistic completed successfully for id: {} and quantity: {}", id, quantity);

        return product;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public ResponseEntity<ApiResponseDto> buyProduct(Long id, Long quantity) {
        // 유저 생기면 로그인 여부 확인
        Products product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 제품이 존재하지 않습니다.")
        );

        if (product.getAmount() <= 0) {
            throw new IllegalArgumentException("매진 되었습니다.");
        } else if (product.getAmount() > 0 && product.getAmount() - quantity < 0) {
            throw new IllegalArgumentException("해당 제품은 총" + product.getAmount() + "개 남아있습니다.");
        } else {
            Orders order = new Orders();
            order.setAmount(quantity);
            order.setOrder_date(new Date());
            order.setProducts(product);
            order.setProduct_price(product.getCost());
            order.setTotal_price(product.getCost() * quantity);

            orderRepository.saveAndFlush(order);

            product.buy(quantity);
            // order에 산 만큼 저장
            // 이후 로직 있으면 더 추가
            productRepository.saveAndFlush(product);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponseDto("주문 완료", HttpStatus.ACCEPTED.value()));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<ApiResponseDto> buyPessimistic(Long id, Long quantity) {
        logger.info("buyPessimistic started for id: {} and quantity: {}", id, quantity);

        try {
            Products products = productRepository.findByIdWithPessimisticLock(id);
//            Products products = productRepository.findById(id).orElseThrow(
//                    () -> new NullPointerException("해당 제품이 존재하지 않습니다")
//            );
            logger.info("Current amount (visible to this thread): {}", products.getAmount());

            // 현재 재고 확인
            Long currentStock = products.getAmount();

            if (currentStock >= quantity) {
                products.buy(quantity);

                productRepository.saveAndFlush(products);
                logger.info("buyPessimistic completed successfully for id: {} and quantity: {}", id, quantity);

                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ApiResponseDto("주문 완료", HttpStatus.ACCEPTED.value()));
            }
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto("재고가 부족해여", HttpStatus.BAD_REQUEST.value()));


        } catch (Exception e) {
            logger.error("buyPessimistic failed with error: {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
