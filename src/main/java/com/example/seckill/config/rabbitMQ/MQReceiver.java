package com.example.seckill.config.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
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

  @RabbitListener(queues = "queue_topic01")
  public void receive05(Object message) {
    log.info("Queue01 接收消息: " + message);
  }

  @RabbitListener(queues = "queue_topic02")
  public void receive06(Object message) {
    log.info("Queue02 接收消息: " + message);
  }

  @RabbitListener(queues = "queue_headers01")
  public void receive07(Message message) {
    log.info("QUEUE01接收Message对象: " + message);
    log.info("QUEUE01接收消息: " + new String(message.getBody()));
  }

  @RabbitListener(queues = "queue_headers02")
  public void receive08(Message message) {
    log.info("QUEUE02接收Message对象: " + message);
    log.info("QUEUE02接收消息: " + new String(message.getBody()));
  }

}
