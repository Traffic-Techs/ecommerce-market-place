package com.tnt.ecommeracemarketplace.repository;

import static com.tnt.ecommeracemarketplace.entity.QProducts.products;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tnt.ecommeracemarketplace.entity.Products;
import com.tnt.ecommeracemarketplace.entity.QProducts;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryQueryImpl implements ProductRepositoryQuery{
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Products> search(ProductSearchCond cond) {
    var query = query(products, cond);

    query.orderBy(products.title.desc());

    var products = query.fetch();

    return products;
  }

  private <T> JPAQuery<T> query(Expression<T> expr, ProductSearchCond cond) {
    return jpaQueryFactory.select(expr)
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
