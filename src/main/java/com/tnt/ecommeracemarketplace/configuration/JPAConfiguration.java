package com.tnt.ecommeracemarketplace.configuration;

import com.tnt.ecommeracemarketplace.products.ProductRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = ProductRepository.class)
public class JPAConfiguration {

}