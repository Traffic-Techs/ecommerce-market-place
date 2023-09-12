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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

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

    @Override
    @Transactional
    public void buyProduct(Long id, Long quantity) {
        // 유저 생기면 로그인 여부 확인
        Products product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 제품이 존재하지 않습니다.")
        );

        if (product.getAmount() <= 0) {
            throw new IllegalArgumentException("매진 되었습니다.");
        }
        else if (product.getAmount() > 0 && product.getAmount() - quantity < 0) {
            throw new IllegalArgumentException("해당 제품은 총" + product.getAmount() + "개 남아있습니다.");
        }

        product.buy(quantity);
        // order에 산 만큼 저장
        // 이후 로직 있으면 더 추가
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void buyPessimistic (Long id, Long quantity) {
        Products products = productRepository.findByIdWithPessimisticLock(id);

        if (products.getAmount() <= 0) {
            throw new IllegalArgumentException("매진 되었습니다.");
        }
        else if (products.getAmount() < quantity) {
            throw new IllegalArgumentException("해당 제품은 총" + products.getAmount() + "개 남아있습니다.");
        }

        products.buy(quantity);

        productRepository.save(products);

        Orders order = new Orders();
        order.setAmount(quantity);
        order.setOrder_date(new Date());
        order.setProducts(products);
        order.setProduct_price(products.getCost());
        order.setTotal_price(products.getCost() * quantity);

        orderRepository.save(order);
    }
}
