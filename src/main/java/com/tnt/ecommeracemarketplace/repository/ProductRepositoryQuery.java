package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.Products;
import java.util.List;

public interface ProductRepositoryQuery {

  // JPAQueryFactory 검색
  List<Products> search(ProductSearchCond cond);
}
