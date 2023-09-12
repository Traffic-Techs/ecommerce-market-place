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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

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

  @Override
  public ProductResponseDto findProductDetails(Long productId) {

    Products productToFind = productRepository.findById(productId).orElseThrow(
        () -> new NullPointerException("해당 제품이 존재하지 않습니다")
    );

    System.out.println(productToFind.getTitle());

    return new ProductResponseDto(productToFind);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void buyProduct(Long id, Long quantity) {
    // 유저 생기면 로그인 여부 확인
    Products product = productRepository.findById(id).orElseThrow(
        () -> new NullPointerException("해당 제품이 존재하지 않습니다.")
    );

    if (product.getAmount() <= 0) {
      throw new IllegalArgumentException("매진 되었습니다.");
    } else if (product.getAmount() > 0 && product.getAmount() - quantity < 0) {
      throw new IllegalArgumentException("해당 제품은 총" + product.getAmount() + "개 남아있습니다.");
    }

    product.buy(quantity);
    // order에 산 만큼 저장
    // 이후 로직 있으면 더 추가
    productRepository.saveAndFlush(product);

    Orders order = new Orders();
    order.setAmount(quantity);
    order.setOrder_date(new Date());
    order.setProducts(product);
    order.setProduct_price(product.getCost());
    order.setTotal_price(product.getCost() * quantity);

    orderRepository.saveAndFlush(order);
  }

//    @Transactional
//    public void buyPessimistic (Long id, Long quantity) {
//        try {
////            Products products = productRepository.findByIdWithPessimisticLock(id);
//
//            Products products = productRepository.findById(id, LockModeType.PESSIMISTIC_WRITE);
//
//            if (products.getAmount() <= 0) {
//                throw new IllegalArgumentException("매진 되었습니다.");
//            }
//            else if (products.getAmount() < quantity) {
//                throw new IllegalArgumentException("해당 제품은 총" + products.getAmount() + "개 남아있습니다.");
//            }
//
//            products.buy(quantity);
//
//            productRepository.save(products);
//
//            Orders order = new Orders();
//            order.setAmount(quantity);
//            order.setOrder_date(new Date());
//            order.setProducts(products);
//            order.setProduct_price(products.getCost());
//            order.setTotal_price(products.getCost() * quantity);
//
//            orderRepository.save(order);
//        } catch (Exception e) {
//            throw e;
//        }
//    }

}
