package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

  List<Orders> findAllByProductsId(Long productId);
}
