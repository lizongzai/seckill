//package com.example.seckill.config.rabbitMQ;
//
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.HeadersExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class RabbitMQHeadersConfig {
//
//
//  private static final String QUEUE01_HEADERS = "queue_headers01";
//  private static final String QUEUE02_HEADERS = "queue_headers02";
//  private static final String EXCHANGE_HEADERS = "headersExchange";
//
//  @Bean
//  public Queue queue01() {
//    return new Queue(QUEUE01_HEADERS);
//  }
//
//  @Bean
//  public Queue queue02() {
//    return new Queue(QUEUE02_HEADERS);
//  }
//
//  @Bean
//  public HeadersExchange headersExchange() {
//    return new HeadersExchange(EXCHANGE_HEADERS);
//  }
//
//  @Bean
//  public Binding binding01() {
//    Map<String, Object> map = new HashMap<>();
//    map.put("color", "red");
//    map.put("speed", "low");
//    return BindingBuilder.bind(queue01()).to(headersExchange()).whereAny(map).match();
//  }
//
//  /**
//   * 键值对同时满足才可以匹配
//   *
//   * @return
//   */
//  @Bean
//  public Binding binding02() {
//    Map<String, Object> map = new HashMap<>();
//    map.put("color", "green");
//    map.put("speed", "fast");
//    return BindingBuilder.bind(queue02()).to(headersExchange()).whereAll(map).match();
//  }
//
//}
