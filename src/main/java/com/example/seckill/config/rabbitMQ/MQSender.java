package com.example.seckill.config.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
   * 功能描述: Fanout工作模式 1.将消失发送到fanoutExchange
   *
   * @param message
   */
  public void sendFanout(Object message) {
    log.info("发送消息: " + message);
    rabbitTemplate.convertAndSend("fanoutExchange", "", message);
  }

  /**
   * 功能描述: Direct直连模式 1.将消失发送到directExchange
   *
   * @param message
   */
  public void sendDirect01(Object message) {
    log.info("发送Red消息: " + message);
    rabbitTemplate.convertAndSend("directExchange", "queue.red", message);
  }

  /**
   * 功能描述: Direct直连模式 1.将消失发送到directExchange
   *
   * @param message
   */
  public void sendDirect02(Object message) {
    log.info("发送Green消息: " + message);
    rabbitTemplate.convertAndSend("directExchange", "queue.green", message);
  }

  /**
   * 功能描述: Topic模式 1.将消失发送到topicExchange
   *
   * @param message
   */
  public void sendTopic01(Object message) {
    log.info("发送Topic01消息: " + message);
    rabbitTemplate.convertAndSend("topicExchange", "queue.green", message);
  }

  /**
   * 功能描述: Topic模式 1.将消失发送到topicExchange
   *
   * @param message
   */
  public void sendTopic02(Object message) {
    log.info("发送Topic02消息: " + message);
    rabbitTemplate.convertAndSend("topicExchange", "queue.green.message", message);
  }

  /**
   * 功能描述: Topic模式 1.将消失发送到topicExchange
   *
   * @param message
   */
  public void sendTopic03(Object message) {
    log.info("发送Topic03消息: " + message);
    rabbitTemplate.convertAndSend("topicExchange", "message.queue.green.hello", message);
  }

  /**
   * 功能描述: Headers模式
   *
   * @param msg
   */
  public void sendHeaders01(String msg) {
    log.info("发送消息(被Queue01队列接收): " + msg);
    MessageProperties properties = new MessageProperties();
    properties.setHeader("color", "red");
    properties.setHeader("speed", "normal");
    Message message = new Message(msg.getBytes(),properties);
    rabbitTemplate.convertAndSend("headersExchange", "", message);
  }

  /**
   * 功能描述: Headers模式
   *
   * @param message
   */
  public void sendHeaders02(String message) {
    log.info("发送消息(被两个队列接收): " + message);
    MessageProperties properties = new MessageProperties();
    properties.setHeader("color", "red");
    properties.setHeader("speed", "fast");
    Message msg = new Message(message.getBytes(),properties);
    rabbitTemplate.convertAndSend("headersExchange", "", msg);
  }

}
