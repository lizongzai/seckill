package com.example.seckill.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis工具类
 *
 * @author lizongzai
 * @date 2023/02/11 17:07
 * @since 1.0.0
 */
@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {

    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    //String类型key设置序列器
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    //String类型value设置序列其
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    //Hash类型key设置序列器
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    //Hash类型value设置序列其
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    //注入连接工厂
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    //返回结果
    return redisTemplate;
  }

}
