//package com.tnt.ecommeracemarketplace.productRedis;
//
//import java.util.Arrays;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//
//@SpringBootTest
//public class RedisTest {
//    @Autowired
//    StringRedisTemplate redisTemplate;
//
//    // String
//    @Test
//    public void testString() {
//        final String key = "testString";
//        final ValueOperations<String,String> stringStringValueOperations = redisTemplate.opsForValue();
//
//        stringStringValueOperations.set(key, "1");
//        final String ret1 = stringStringValueOperations.get(key);
//
//        System.out.println("ret1 = " + ret1);
//    }
//
//    // List
//    @Test
//    public void testList() {
//        final String key = "testList";
//        final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
//
//        stringStringListOperations.rightPush(key, "H");
//        stringStringListOperations.rightPush(key, "I");
//
//        stringStringListOperations.rightPushAll(key, "H","E","L","L","O");
//
//        final String char1 = stringStringListOperations.index(key,1);
//        System.out.println("char1 = " + char1 );
//
//        final Long size = stringStringListOperations.size(key);
//        System.out.println("size = " + size );
//
//        final List<String>  retRange = stringStringListOperations.range(key, 2,6);
//
//        assert retRange != null;
//        System.out.println("result range = " + Arrays.toString(retRange.toArray()));
//    }
//}
