//package com.tnt.ecommeracemarketplace.product;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import com.tnt.ecommeracemarketplace.entity.Products;
//import com.tnt.ecommeracemarketplace.repository.ProductRepository;
//import com.tnt.ecommeracemarketplace.service.ProductService;
//import com.tnt.ecommeracemarketplace.service.ProductServiceImpl;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class ProductBuyTest {
//
//    @Autowired
//    private ProductServiceImpl productService;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Test
//    public void 동시에_100개의_요청 () throws InterruptedException {
//        int threadCount = 100;
//        // 비동기로 실행하는 작업을 단순화하여 사용할 수 있게 도와주는 자바 API (ExecutorService)
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        // 다른 쓰레드에서 수행하는 작업이 끝날 때까지 대기하게 도와주는 클래스
//        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
//
//        for (int i = 0; i < threadCount; i++) {
//            executorService.submit(() -> {
//                try {
//                    productService.buyPessimistic(10L, 1L);
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await();
//
//        Products products = productRepository.findById(1L).orElseThrow();
//
//        assertEquals(0, products.getAmount());
//    }
//}
