package com.tnt.ecommeracemarketplace.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {

  // 전체 조회
  Page<Products> findAll(Pageable pageable);
}
