package com.example.seckill.controller;


import com.example.seckill.pojo.User;
import com.example.seckill.service.IOrderService;
import com.example.seckill.vo.OrderDetailVo;
import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀功能: 订单详情
 *
 * @author lizongzai
 * @since 2023-02-12
 */
@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private IOrderService orderService;

  @RequestMapping(value = "/detail")
  @ResponseBody
  public RespBean detail(User user, Long orderId) {

    //判断用户是否为空
    if (user == null) {
      return RespBean.error(RespBeanEnum.USER_NOT_EXIST);
    }

    //获取订单详情
    OrderDetailVo detail = orderService.detail(orderId);
    return RespBean.success(detail);
  }
}
