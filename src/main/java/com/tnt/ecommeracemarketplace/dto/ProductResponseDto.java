package com.tnt.ecommeracemarketplace.dto;

import com.tnt.ecommeracemarketplace.entity.Products;
import java.util.Date;
import lombok.Getter;

@Getter
public class ProductResponseDto {

  private Long id;
  private String title;
  private String images;
  private String category;
  private Long cost;
  private Long amount;
  private Boolean sale;
  private Date register_date;

  public ProductResponseDto(Products product) {
    this.id = product.getId();
    this.title = product.getTitle();
    this.images = product.getImages();
    this.category = product.getCategory();
    this.cost = product.getCost();
    this.amount = product.getAmount();
    this.sale = product.getSale();
    this.register_date = product.getRegister_date();
  }
}
