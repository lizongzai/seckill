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

  /**
   * 功能描述: Fanout工作模式
   * 1.将消失发送到fanout_exchange
   *
   * @param message
   */
  public void sendFanout(Object message) {
    log.info("发送消息: " + message);
    rabbitTemplate.convertAndSend("fanoutExchange", "", message);
  }

  public void sendDirect01(Object message) {
    log.info("发送Red消息: " + message);
    rabbitTemplate.convertAndSend("directExchange", "queue.red", message);
  }

  public void sendDirect02(Object message) {
    log.info("发送Green消息: " + message);
    rabbitTemplate.convertAndSend("directExchange", "queue.green", message);
  }
}
