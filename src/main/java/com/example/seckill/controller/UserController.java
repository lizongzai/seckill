package com.example.seckill.controller;


import com.example.seckill.config.rabbitMQ.MQSender;
import com.example.seckill.pojo.User;
import com.example.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: 用户信息(仅用于压力测试)
 *
 * @author lizongzai
 * @since 2023-02-09
 */
@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private MQSender mqSender;

  /**
   * 功能描述: 用户信息测试
   *
   * @param user
   * @return
   */
  public RespBean info(User user) {
    return RespBean.success(user);
  }

  /**
   * 功能描述: 测试RabbitMQ消息
   */
  @RequestMapping("/mq")
  @ResponseBody
  public void mq() {
    mqSender.send("hello world");
  }


  /**
   * 功能描述: Fanout工作模式
   */
  @RequestMapping("/mq/fanout")
  @ResponseBody
  public void mqFanout() {
    mqSender.sendFanout("hello, fanout");
  }

  /**
   * 功能描述: Direct直连模式
   */
  @RequestMapping("/mq/direct01")
  @ResponseBody
  public void mqDirect01() {
    mqSender.sendDirect01("hello, red");
  }

  /**
   * 功能描述: Direct直连模式
   */
  @RequestMapping("/mq/direct02")
  @ResponseBody
  public void mqDirect02() {
    mqSender.sendDirect02("hello, green");
  }



}
