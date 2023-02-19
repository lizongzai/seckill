package com.example.seckill.config.rabbitMQ;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ消息队列配置类
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Configuration
public class RabbitMQConfig {

  @Bean
  public Queue queue() {
    return new Queue("queue", true);
  }
}
