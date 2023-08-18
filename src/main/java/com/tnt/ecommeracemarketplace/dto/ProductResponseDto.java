package com.tnt.ecommeracemarketplace.dto;

import com.tnt.ecommeracemarketplace.entity.Products;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
  private Long id;
  private String title;
  private String images;
  private String description;
  private Long cost;
  private Long amount;
  private Date register_date;
  private Boolean sale;

  public ProductResponseDto (Products product) {
    this.id = product.getId();
    this.title = product.getTitle();
    this.images = product.getImages();
    this.description = product.getDescription();
    this.cost = product.getCost();
    this.amount = product.getAmount();
    this.register_date = product.getRegister_date();
    this.sale = product.getSale();
  }
}
