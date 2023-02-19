package com.example.seckill.config.rabbitMQ;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Direct直连配置类
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Configuration
@Slf4j
public class RabbitMQDirectConfig {

  private static final String QUEUE01_DIRECT = "queue_direct01";
  private static final String QUEUE02_DIRECT = "queue_direct02";
  private static final String EXCHANGE_DIRECT = "directExchange";
  private static final String ROUTINGKEY01 = "queue.red";
  private static final String ROUTINGKEY02 = "queue.green";

  @Bean
  public Queue queue01() {
    return new Queue(QUEUE01_DIRECT);
  }

  @Bean
  public Queue queue02() {
    return new Queue(QUEUE02_DIRECT);
  }

  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange(EXCHANGE_DIRECT);
  }

  @Bean
  public Binding binding01() {
    return BindingBuilder.bind(queue01()).to(directExchange()).with(ROUTINGKEY01);
  }

  @Bean
  public Binding binding02() {
    return BindingBuilder.bind(queue02()).to(directExchange()).with(ROUTINGKEY02);
  }

}
