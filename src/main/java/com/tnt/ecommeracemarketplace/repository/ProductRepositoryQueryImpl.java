package com.tnt.ecommeracemarketplace.repository;

import static com.tnt.ecommeracemarketplace.entity.QProduct.product;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tnt.ecommeracemarketplace.entity.Product;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ProductRepositoryQueryImpl implements ProductRepositoryQuery{
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Product> search(ProductSearchCond cond, Pageable pageable) {
    var query = query(product, cond)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    query.orderBy(product.register_date.desc());

    var products = query.fetch();

    return products;
  }

  private <T> JPAQuery<T> query(Expression<T> expr, ProductSearchCond cond) {
    return jpaQueryFactory.select(expr)
        .from(product)
        .where(
            productTitleEq(cond.getKeyword())
        );
  }

  private JPAQuery<Long> countQuery(ProductSearchCond cond) {
    return jpaQueryFactory.select(Wildcard.count)
        .from(product)
        .where(
            productTitleEq(cond.getKeyword())
        );
  }

  private BooleanExpression productTitleEq(String keyword) {
    return Objects.nonNull(keyword) ? product.title.eq(keyword) : null;
  }

}
