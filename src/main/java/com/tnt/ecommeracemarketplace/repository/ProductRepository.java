package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.Products;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long>, ProductRepositoryQuery {

  Products findOneById(Long productId);

  // Spring Data 검색
  List<Products> findByTitleContainingIgnoreCase (String keyword);
}
