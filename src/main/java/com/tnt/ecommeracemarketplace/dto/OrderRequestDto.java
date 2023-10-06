package com.tnt.ecommeracemarketplace.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    private Long productId;
    private String productTitle;
    private Long quantity;

    public OrderRequestDto(Long productId, String productTitle, Long quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
    }
}
