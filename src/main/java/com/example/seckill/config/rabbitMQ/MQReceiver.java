package com.example.seckill.config.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

  @RabbitListener(queues = "queue_fanout01")
  public void receive01(Object message) {
    log.info("Queue01 接收消息: " + message);
  }

  @RabbitListener(queues = "queue_fanout02")
  public void receive02(Object message) {
    log.info("Queue02 接收消息: " + message);
  }

  @RabbitListener(queues = "queue_direct01")
  public void receive03(Object message) {
    log.info("Queue01 接收消息: " + message);
  }

  @RabbitListener(queues = "queue_direct02")
  public void receive04(Object message) {
    log.info("Queue02 接收消息: " + message);
  }
}
