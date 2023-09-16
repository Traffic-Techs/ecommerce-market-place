package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.entity.Orders;
import com.tnt.ecommeracemarketplace.entity.Products;
import com.tnt.ecommeracemarketplace.repository.OrderRepository;
import com.tnt.ecommeracemarketplace.repository.ProductRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

  // 전체 조회
  @Override
  public ProductListResponseDto getProducts(PageDto pageDto) {
    Pageable pageable = pageDto.toPageable();
    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    Page<Products> productPage = productRepository.findAll(pageable);

    List<ProductResponseDto> productList = productPage.getContent().stream()
        .map(ProductResponseDto::new)
        .collect(Collectors.toList());

    return new ProductListResponseDto(productList);
  }

  @Transactional(readOnly = true)
  @Cacheable(value = "product", key = "#productId")
  @Override
  public ProductResponseDto findProductDetails(Long productId) {

    Products productToFind = productRepository.findById(productId).orElseThrow(
        () -> new NullPointerException("해당 제품이 존재하지 않습니다")
    );

    System.out.println(productToFind.getTitle());

    return new ProductResponseDto(productToFind);
  }

  public ProductListResponseDto findProducts(String keyword, PageDto pageDto) {
    Pageable pageable = pageDto.toPageable();
    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    Page<Products> productPage = productRepository.findAllByTitle(keyword, pageable);

    List<ProductResponseDto> productList = productPage.getContent().stream()
        .map(ProductResponseDto::new)
        .collect(Collectors.toList());

    return new ProductListResponseDto(productList);
  }

  // 키워드(LIKE + 와일드카드) 검색
//  public ProductListResponseDto selectProductList(String keyword, PageDto pageDto) {
//    Pageable pageable = pageDto.toPageable();
//    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
//    Page<Products> productPage = productRepository.searchByLike(keyword, pageable);
//
//    List<ProductResponseDto> productList = productPage.getContent().stream()
//        .map(ProductResponseDto::new)
//        .collect(Collectors.toList());
//
//    return new ProductListResponseDto(productList);
//  }

  // 키워드(Full Text) 검색
//  public ProductListResponseDto selectProductList(String keyword, PageDto pageDto) {
//    Pageable pageable = pageDto.toPageable();
//    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
//    Page<Products> productPage = productRepository.searchByFullText(keyword, pageable);
//
//    List<ProductResponseDto> productList = productPage.getContent().stream()
//        .map(ProductResponseDto::new)
//        .collect(Collectors.toList());
//
//    return new ProductListResponseDto(productList);
//  }

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
  public Products productModify(Long id, Long quantity) {

    Products product = productRepository.findByIdWithPessimisticLock(id);

    logger.info("Current amount (visible to this thread): {}", product.getAmount());

        // 재고 부족 예외처리
        if(product.getAmount() < quantity) {
            logger.info("재고가 부족합니다. for id: {} and quantity: {}", id, quantity);
            throw new IllegalArgumentException("재고 부족");
        }

        try {
            product.buy(quantity);
            productRepository.saveAndFlush(product);
        } catch (Exception ex) {
            logger.error("Pessimistic lock을 획득하지 못하고 종료되었습니다. id: {} and quantity: {}", id, quantity, ex);
            throw ex;
        }

        // 상품 재고 차감
//        product.buy(quantity);
//
//        productRepository.saveAndFlush(product);

    logger.info("buyPessimistic completed successfully for id: {} and quantity: {}", id, quantity);

    return product;
  }
}
