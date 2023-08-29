package com.tnt.ecommeracemarketplace.product.repository.repository;

import com.tnt.ecommeracemarketplace.product.entity.Products;
import java.util.List;

public interface ProductRepositoryQuery {

  // JPAQueryFactory 검색
  List<Products> search(ProductSearchCond cond);
}
