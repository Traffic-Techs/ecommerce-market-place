package com.tnt.ecommeracemarketplace.productRedis;

import static com.mysema.commons.lang.Assert.assertThat;

import com.tnt.ecommeracemarketplace.entity.ProductRedis;
import com.tnt.ecommeracemarketplace.repository.ProductRedisRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class ProductRedisTest {

    @Autowired
    private ProductRedisRepository productRedisRepository;

//    @Autowired
//    RedisTemplate<String, Object> redisTemplate;
    @Test
    void redisTest() {
//        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        ProductRedis productRedis = ProductRedis.builder()
            .id("1")
            .title("Apple 2022 맥북 에어")
            .images("https://byline.network/wp-content/uploads/2020/11/Apple_new-macbookair-wallpaper-screen_11102020_big.jpg.medium.jpg")
            .description("APPLE 2022년 맥북에어 M2 칩셋 8코어 CPU, 8코어 GPU, 256GB SSD, 8GB RAM, 미드나이트 (MLY33KH/A) / A2681")
            .amount(1000L)
            .cost(1000000L)
            .sale(true)
            .build();

        ProductRedis savedProduct = productRedisRepository.save(productRedis);

        // Collections.singleton
        //productRedisRepository.findAllById(Collections.singleton(savedProduct.getId()));

        Optional<ProductRedis> findProduct = productRedisRepository.findById(savedProduct.getId());

        assertThat(findProduct.get().getTitle()).isEqualTo(productRedis.getTitle());

//        productRedisRepository.count();
//
//        productRedisRepository.delete(productRedis);

//        valueOperations.set("key", "productRedis");
//        ProductRedis productRedis2 = (ProductRedis) valueOperations.get("key");
//        String redis = (String)valueOperations.get("key");
//        assertThat(redis).isEqualTo("productRedis");
//        assertThat(productRedis2.getTitle()).isEqualTo("Apple 2022 맥북 에어");

//        ProductRedis savedProduct = productRedisRepository.save(productRedis);
//        Optional<ProductRedis> findProduct = productRedisRepository.findById(savedProduct.getId());
//        assertThat(findProduct.get().getTitle()).isEqualTo(productRedis.getTitle());
    }
}
