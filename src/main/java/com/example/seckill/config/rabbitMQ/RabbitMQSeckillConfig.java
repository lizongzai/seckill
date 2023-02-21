package com.example.seckill.config.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ:Topic模式
 *
 * @author lizongzai
 * @since 1.0.0
 */

@Configuration
public class RabbitMQSeckillConfig {

  //秒杀消息队列
  private static final String QUEUE = "seckillQueue";
  //秒杀交换机
  private static final String EXCHANGE = "seckillExchange";

  /**
   * 消息队列
   *
   * @return
   */
  @Bean
  public Queue queue() {
    return new Queue(QUEUE);
  }

  /**
   * 交换机
   *
   * @return
   */
  public TopicExchange topicExchange() {
    return new TopicExchange(EXCHANGE,true,false);
  }

  /**
   * 使用自定义的路由键"seckill.#",将消息队列绑定到交换机
   *
   * @return
   */
  public Binding binding() {
    return BindingBuilder.bind(queue()).to(topicExchange()).with("seckill.message");
  }
}
