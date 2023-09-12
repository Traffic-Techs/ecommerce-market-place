package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.PageDto;
import com.tnt.ecommeracemarketplace.dto.ProductListResponseDto;
import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.entity.ProductEs;
import com.tnt.ecommeracemarketplace.repository.ProductEsRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductEsService {

  private final ProductEsRepository productEsRepository;

  public ProductListResponseDto findProducts(String keyword, PageDto pageDto) {
    Pageable pageable = pageDto.toPageable();
    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    Page<ProductEs> productPage = productEsRepository.findAllByTitle(keyword, pageable);

    List<ProductResponseDto> productList = productPage.getContent().stream()
        .map(ProductResponseDto::new)
        .collect(Collectors.toList());

    return new ProductListResponseDto(productList);
  }
}
