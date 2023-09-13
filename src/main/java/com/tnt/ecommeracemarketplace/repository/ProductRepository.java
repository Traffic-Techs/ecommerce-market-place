package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.Products;
import java.util.List;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Products, Long>, ProductRepositoryQuery {

  Products findOneById(Long productId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select p from Products p where p.id = :id")
  Products findByIdWithPessimisticLock (@Param("id")Long id);

  // Spring Data 검색
  List<Products> findByTitleContainingIgnoreCase (String keyword);

//  Products findById(Long id, LockModeType lockModeType);
}
