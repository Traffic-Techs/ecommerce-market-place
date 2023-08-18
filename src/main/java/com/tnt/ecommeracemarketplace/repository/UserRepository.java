package com.tnt.ecommeracemarketplace.repository;

import com.tnt.ecommeracemarketplace.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
