//package com.example.seckill.config.rabbitMQ;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * RabbitMQ:Topic模式
// *
// * @author lizongzai
// * @since 1.0.0
// */
//@Configuration
//public class RabbitMQTopicConfig {
//
//  private static final String QUEUE01_TOPIC = "queue_topic01";
//  private static final String QUEUE02_TOPIC = "queue_topic02";
//  private static final String EXCHANGE_TOPIC = "topicExchange";
//  private static final String ROUTINGKEY01 = "#.queue.#";
//  private static final String ROUTINGKEY02 = "#.queue.#";
//
//  @Bean
//  public Queue queue01() {
//    return new Queue(QUEUE01_TOPIC);
//  }
//
//  @Bean
//  public Queue queue02() {
//    return new Queue(QUEUE02_TOPIC);
//  }
//
//  @Bean
//  public TopicExchange topicExchange() {
//    return new TopicExchange(EXCHANGE_TOPIC);
//  }
//
//  @Bean
//  public Binding binding01() {
//    return BindingBuilder.bind(queue01()).to(topicExchange()).with(ROUTINGKEY01);
//  }
//
//  @Bean
//  public Binding binding02() {
//    return BindingBuilder.bind(queue02()).to(topicExchange()).with(ROUTINGKEY02);
//  }
//
//}
