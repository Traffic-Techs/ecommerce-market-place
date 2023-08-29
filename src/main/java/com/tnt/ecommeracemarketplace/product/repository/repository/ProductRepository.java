package com.tnt.ecommeracemarketplace.product.repository.repository;

import com.tnt.ecommeracemarketplace.product.entity.Products;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long>, ProductRepositoryQuery {

  // Spring Data 검색
  List<Products> findByTitleContainingIgnoreCase (String keyword);
}
