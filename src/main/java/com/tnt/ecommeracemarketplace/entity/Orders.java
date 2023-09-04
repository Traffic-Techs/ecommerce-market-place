package com.tnt.ecommeracemarketplace.entity;

import jakarta.persistence.*;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @CreatedDate
  private Date order_date;

  @Column(nullable = false)
  private Long amount;

  @Column(nullable = false)
  private Long total_price;

  @Column(nullable = false)
  private Long product_price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Products products;
}
