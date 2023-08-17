package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryQuery {
  List<Product> search(ProductSearchCond cond, Pageable pageable);
}
