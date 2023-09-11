package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.OrderRequestDto;
import com.tnt.ecommeracemarketplace.entity.Orders;
import com.tnt.ecommeracemarketplace.entity.Products;
import com.tnt.ecommeracemarketplace.repository.OrderRepository;
import com.tnt.ecommeracemarketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public void saveOrders(OrderRequestDto requestDto) {
        // 받은 product data의 id 보고 그 것들 전부 찾음
//        List<Orders> ordersProducts = orderRepository.findAllByProductsId(
//                requestDto.getProductId());

        Products product = productRepository.findOneById(requestDto.getProductId());

        // 지금 받은 product를 order로 변환
        Orders order = new Orders();
        order.setAmount(requestDto.getQuantity());
        order.setOrder_date(new Date());
        order.setProducts(product);
        order.setProduct_price(product.getCost());
        order.setTotal_price(product.getCost() * requestDto.getQuantity());

//        int sumOfAmount = 0;
//
//        if (ordersProducts != null) {
//            for (Orders tempOrder : ordersProducts) {
//                sumOfAmount += tempOrder.getAmount();
//            }
//
//            if (sumOfAmount + requestDto.getQuantity() > product.getAmount()) {
//                throw new IllegalArgumentException("제품 수량이 부족합니다.");
//            } else {
//                orderRepository.save(order);
//            }
//        } else {
//            orderRepository.save(order);
//        }

        orderRepository.save(order);
    }

    @Override
    public Long findOrders(Long productId) {
        List<Orders> ordersProducts = orderRepository.findAllByProductsId(productId);
        Long sum = 0L;

        if (ordersProducts != null) {
            for (Orders tempOrder : ordersProducts) {
                sum += tempOrder.getAmount();
            }
        }
        return sum;
    }
}
