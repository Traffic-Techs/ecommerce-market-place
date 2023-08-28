package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryQuery {

  // JPAQueryFactory 검색
  Page<Products> search(ProductSearchCond cond, Pageable pageable);
}
