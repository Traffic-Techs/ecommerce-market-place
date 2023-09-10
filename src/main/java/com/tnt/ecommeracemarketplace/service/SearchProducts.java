package com.tnt.ecommeracemarketplace.service;

import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "products")
public class SearchProducts {

  @Id
  private String id;

  private String title;
}