package com.example.seckill.config.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息发送者
 *
 * @author lizongzai
 * @since 1.0.0
 */

@Service
@Slf4j
public class MQSender {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void send(Object message) {
    log.info("发送消息: " + message);
    rabbitTemplate.convertAndSend("queue", message);
  }
}
