package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.PageDTO;
import com.tnt.ecommeracemarketplace.entity.Product;
import com.tnt.ecommeracemarketplace.repository.ProductSearchCond;
import java.util.List;

public interface ProductService {
  List<Product> selectProductList(String keyword, PageDTO pageDTO);

}
