package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.PageDTO;
import com.tnt.ecommeracemarketplace.entity.Product;
import com.tnt.ecommeracemarketplace.repository.ProductRepository;
import com.tnt.ecommeracemarketplace.repository.ProductSearchCond;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

  private final ProductRepository productRepository;

  @Transactional
  @Override
  public List<Product> selectProductList(String keyword, PageDTO pageDTO) {
    var cond = ProductSearchCond.builder().keyword(keyword).build();
    return productRepository.search(cond, pageDTO.toPageable());
  }
}
