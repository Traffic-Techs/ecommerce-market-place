package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Products, Long> {

  // 전체 조회
  @Query("SELECT p FROM Products p ORDER BY p.register_date DESC")
  Page<Products> findAllOrderByRegisterDateAtDesc(Pageable pageable);

  // 키워드 검색
//  @Query(value = "SELECT * FROM products WHERE MATCH(title) AGAINST(:keyword IN BOOLEAN MODE) ORDER BY register_date DESC", nativeQuery = true)
//  Page<Products> searchByFullText(String keyword, Pageable pageable);

  // 가격대별 검색
//  Page<Products> findByCostBetween(Long minCost, Long maxCost, Pageable pageable);
}
