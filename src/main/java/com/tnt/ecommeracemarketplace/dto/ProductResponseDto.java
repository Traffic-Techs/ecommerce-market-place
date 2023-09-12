package com.tnt.ecommeracemarketplace.dto;

import com.tnt.ecommeracemarketplace.productEs.ProductEs;
import com.tnt.ecommeracemarketplace.products.Products;
import lombok.Getter;

@Getter
public class ProductResponseDto {

  private Long id;
  private String title;
  private String images;
  private String description;
  private Long cost;
  private Long amount;
  private Boolean sale;

  public ProductResponseDto(Products product) {
    this.id = product.getId();
    this.title = product.getTitle();
    this.images = product.getImages();
    this.description = product.getDescription();
    this.cost = product.getCost();
    this.amount = product.getAmount();
    this.sale = product.getSale();
  }

  public ProductResponseDto(ProductEs productEs) {
    this.id = productEs.getId();
    this.title = productEs.getTitle();
    this.images = productEs.getImages();
    this.description = productEs.getDescription();
    this.cost = productEs.getCost();
    this.amount = productEs.getAmount();
    this.sale = productEs.getSale();
  }
}
