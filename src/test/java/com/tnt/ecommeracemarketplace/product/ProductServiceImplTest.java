//package com.tnt.ecommeracemarketplace.product;
//
//import com.tnt.ecommeracemarketplace.products.Products;
//import com.tnt.ecommeracemarketplace.entity.Users;
//import com.tnt.ecommeracemarketplace.products.ProductRepository;
//import com.tnt.ecommeracemarketplace.repository.UserRepository;
//import com.tnt.ecommeracemarketplace.products.ProductService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class ProductServiceImplTest {
//
//    @Autowired
//    ProductRepository productRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    ProductService productService;
//
//    @Test
//    @DisplayName("키워드 검색으로 상품을 찾는 기능 테스트")
//    void selectProductListTest() {
//        // given
//        var user = getTestUser("1", "1", "1", "1");
//        var product1 = getTestProduct("computer", "1", "1", 1L, 1L);
//        var product2 = getTestProduct("macBook", "1", "1", 1L, 1L);
//        var product3 = getTestProduct("noteBook", "1", "1", 1L, 1L);
//
//        // when
//        var productList = productService.selectProductList("computer");
//
//        // then
//        assertEquals(1, productList.size());
//    }
//
//    private Users getTestUser(String username, String password, String address, String nickname) {
//        var newUser = Users.builder().username(username).password(password).address(address).nickname(nickname).build();
//        return userRepository.save(newUser);
//    }
//
//    private Products getTestProduct(String title, String images, String description, Long cost, Long amount) {
//        var newProduct = Products.builder().title(title).images(images).description(description).cost(cost).amount(amount).build();
//        return productRepository.save(newProduct);
//    }
//
//}
