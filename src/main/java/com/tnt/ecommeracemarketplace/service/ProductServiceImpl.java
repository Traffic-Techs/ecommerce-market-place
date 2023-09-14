package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.entity.Orders;
import com.tnt.ecommeracemarketplace.entity.Products;
import com.tnt.ecommeracemarketplace.repository.OrderRepository;
import com.tnt.ecommeracemarketplace.repository.ProductRepository;
import com.tnt.ecommeracemarketplace.repository.ProductSearchCond;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
    @Override
    @Transactional
    public void orderProducts(Long id, Long quantity) {

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
}
