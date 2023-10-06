package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.Products;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Products, Long> {

  // 전체 조회
  Page<Products> findAll(Pageable pageable);

  Products findOneById(Long productId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select p from Products p where p.id = :id")
  Products findByIdWithPessimisticLock(Long id);

  // Spring Data 검색
  Page<Products> findAllByTitle(String keyword, Pageable pageable);

  // Like + 와일드카드 검색
  @Query(value = "SELECT * FROM products WHERE title LIKE %:keyword%", nativeQuery = true)
  Page<Products> searchByLike(String keyword, Pageable pageable);


  // 키워드 검색
  @Query(value = "SELECT * FROM products WHERE MATCH(title) AGAINST(:keyword IN BOOLEAN MODE)", nativeQuery = true)
  Page<Products> searchByFullText(String keyword, Pageable pageable);
}
