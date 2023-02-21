package com.example.seckill.config.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息发送者(或消息生成者)
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Service
@Slf4j
public class MQSeckillSender {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  /**
   * 发送秒杀信息
   *
   * @Param: [message]
   */
  public void sendSeckillMessage(String message) {
    log.info("发送秒杀信息：" + message);
    rabbitTemplate.convertAndSend("seckillExchange", "seckill.message", message);
  }
}
