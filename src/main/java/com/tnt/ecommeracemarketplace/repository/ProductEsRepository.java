package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.ProductEs;
import com.tnt.ecommeracemarketplace.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductEsRepository extends ElasticsearchRepository<ProductEs, String> {

  Page<Products> findByTitle(String keyword, Pageable pageable);
}
