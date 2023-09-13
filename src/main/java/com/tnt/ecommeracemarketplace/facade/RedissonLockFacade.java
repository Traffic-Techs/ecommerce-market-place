package com.tnt.ecommeracemarketplace.facade;

import com.tnt.ecommeracemarketplace.dto.ApiResponseDto;
import com.tnt.ecommeracemarketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockFacade {

    private final RedissonClient redissonClient;
    private final ProductService productService;

    public ResponseEntity<ApiResponseDto> buy (Long id, Long quantity) {
        RLock lock = redissonClient.getLock(id.toString());

        try {
            boolean available = lock.tryLock(10, 10, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("lock 획득 실패");
            }
            // productService에서 제품을 구매하는 로직이 필요한 부분
            return productService.buyProduct(id, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
