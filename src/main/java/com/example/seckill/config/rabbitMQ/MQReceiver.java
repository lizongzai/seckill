package com.example.seckill.config.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 消息接收者(或消失消费者)
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Service
@Slf4j
public class MQReceiver {

  @RabbitListener(queues = "queue")
  public void receive(Object message) {
    log.info("消息接收: " + message);
  }
}
