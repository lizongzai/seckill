package com.example.seckill.controller;


import com.example.seckill.pojo.User;
import com.example.seckill.vo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

  public RespBean info(User user) {
    return RespBean.success(user);
  }


}
