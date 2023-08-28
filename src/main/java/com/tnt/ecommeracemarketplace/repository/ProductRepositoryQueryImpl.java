package com.tnt.ecommeracemarketplace.repository;

import static com.tnt.ecommeracemarketplace.entity.QProducts.products;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tnt.ecommeracemarketplace.entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ProductRepositoryQueryImpl implements ProductRepositoryQuery {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<Products> search(ProductSearchCond cond, Pageable pageable) {
    var query = query(products, cond)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    var products = query.fetch();
    long totalSize = countQuery(cond).fetch().get(0);

    return PageableExecutionUtils.getPage(products, pageable, () -> totalSize);
  }

  private <T> JPAQuery<T> query(Expression<T> expr, ProductSearchCond cond) {
    return jpaQueryFactory.select(expr)
        .from(products)
        .where(
            productTitleContains(cond.getKeyword())
        );
  }

  private JPAQuery<Long> countQuery(ProductSearchCond cond) {
    return jpaQueryFactory.select(Wildcard.count)
        .from(products)
        .where(
            productTitleContains(cond.getKeyword())
        );
  }

  private BooleanExpression productTitleContains(String keyword) {
//    return Objects.nonNull(keyword) ? products.title.eq(keyword) : null;
    return products.title.containsIgnoreCase(keyword);
  }

}
