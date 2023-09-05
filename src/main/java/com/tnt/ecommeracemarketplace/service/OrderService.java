package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.OrderRequestDto;

public interface OrderService {

    /**
     * order 리스트 받아오기
     *
     * @return order 리스트
     */
    void saveOrders(OrderRequestDto requestDto);

    Integer findOrders(Long productId);
}
