package com.tnt.ecommeracemarketplace.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    private int productId;
    private String productTitle;
    private int quantity;

    public OrderRequestDto(int productId, String productTitle, int quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
    }
}
