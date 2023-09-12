package com.tnt.ecommeracemarketplace.facade;

import com.tnt.ecommeracemarketplace.service.ProductService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedissonLockFacade {

  private final RedissonClient redissonClient;
  private final ProductService productService;

  public void buy(Long id, Long quantity) {
    RLock lock = redissonClient.getLock(id.toString());

    try {
      boolean available = lock.tryLock(5, 3, TimeUnit.SECONDS);
      if (!available) {
        System.out.println("lock 획득 실패");
        return;
      }
      // productService에서 제품을 구매하는 로직이 필요한 부분
      productService.buyProduct(id, quantity);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }
  }
}
