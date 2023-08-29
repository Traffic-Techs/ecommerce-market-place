package com.tnt.ecommeracemarketplace.product.repository.repository;

import static com.tnt.ecommeracemarketplace.entity.QProducts.products;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tnt.ecommeracemarketplace.product.entity.Products;
import java.util.List;
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
