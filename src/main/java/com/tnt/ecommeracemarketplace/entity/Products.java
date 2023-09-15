package com.tnt.ecommeracemarketplace.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Products {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String images;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Long cost;

  @Column(nullable = false)
  private Long amount;

  @Column(nullable = false)
  private Boolean sale;

  @Builder
  public Products(String title, String images, String description, Long cost, Long amount,
      Boolean sale) {
    this.title = title;
    this.images = images;
    this.description = description;
    this.cost = cost;
    this.amount = amount;
    this.sale = sale;
  }

  @Transactional
  public void buy(Long amount) {

    if (this.amount - amount < 0) {
      throw new RuntimeException("재고는 0개 미만이 될 수 없습니다.");
    }

    this.amount -= amount;
  }
}
