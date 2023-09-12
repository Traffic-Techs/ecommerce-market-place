package com.tnt.ecommeracemarketplace.dto;

import com.tnt.ecommeracemarketplace.entity.Orders;
import java.util.Date;

public class OrderResponseDto {

  private Long id;
  private Long amount;
  private Date order_date;
  private Long total_price;

  public OrderResponseDto(Orders order) {
    this.id = order.getId();
    this.amount = order.getAmount();
    this.order_date = order.getOrder_date();
    this.total_price = order.getTotal_price();
  }
}
