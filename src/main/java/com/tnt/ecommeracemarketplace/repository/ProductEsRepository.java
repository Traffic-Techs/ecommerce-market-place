package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.ProductEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductEsRepository extends ElasticsearchRepository<ProductEs, String> {

  Page<ProductEs> findAllByTitle(String keyword, Pageable pageable);
}
