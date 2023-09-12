package com.tnt.ecommeracemarketplace.productEs;

import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "products")
@Getter
@Setter
public class ProductEs {

  @Id
  private Long id;

  private String title;

  private Long cost;

  private String images;

  private String description;

  private Long amount;

  private Boolean sale;
}
