package com.tnt.ecommeracemarketplace.configuration;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfiguration implements CachingConfigurer {

  @Value("${spring.cache.redis.host}")
  private String host;

  @Value("${spring.cache.redis.port}")
  private int port;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }

  // 좀더 복잡한 구성이 필요할 때 아래 설정을 사용한다 -> 예를 들면 예를 들어, 비밀번호, 데이터베이스 인덱스 등을 추가로 설정
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(
//                new RedisStandaloneConfiguration(host, port)
//        );
//    }

  /**
   * RedisTemplate을 사용해서 데이터를 직렬화 해서 저장하고 꺼내오는 역할
   *
   * @return RedisTemplate
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());

    // using UTF-8.
//        redisTemplate.setKeySerializer(new StringRedisSerializer());

    // Java 기본 직렬화가 아닌 JSON 직렬화 설정 - ObjectMapper for default typing.
    redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }

  @Bean
  public CacheManager productCacheManager(RedisConnectionFactory cf) {
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
            new StringRedisSerializer())) // 캐시의 키를 직렬화(serialize)하는 방식을 지정
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
            new GenericJackson2JsonRedisSerializer())) // 캐시의 값(value)을 직렬화하는 방식을 지정
        .entryTtl(Duration.ofMinutes(3L)) // 캐시된 엔트리들의 만료 시간(TTL - Time to Live)을 설정
        .disableCachingNullValues(); // null 값을 캐시로 저장하지 않도록 하는 옵션;

    return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf)
        .cacheDefaults(redisCacheConfiguration).build();
  }
}
