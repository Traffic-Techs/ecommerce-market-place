package com.tnt.ecommeracemarketplace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product {

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
  @CreatedDate
  private Date register_date;

  @Column(nullable = false)
  private Boolean sale;
}
