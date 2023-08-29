package com.tnt.ecommeracemarketplace.product.repository.repository;

import com.tnt.ecommeracemarketplace.product.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
