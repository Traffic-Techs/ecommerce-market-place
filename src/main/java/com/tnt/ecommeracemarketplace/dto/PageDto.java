package com.tnt.ecommeracemarketplace.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDto {

  private Integer currentPage;
  private Integer size;
  private String sortBy;

  public Pageable toPageable() {
    int page = currentPage != null ? currentPage - 1 : 0;
    int pageSize = size != null ? size : 100;
    if (Objects.isNull(sortBy)) {
      return PageRequest.of(page, pageSize);
    } else {
      return PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
    }
  }
}
