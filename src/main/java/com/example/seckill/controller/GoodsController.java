package com.example.seckill.controller;

import com.example.seckill.pojo.User;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

  /**
   * 功能描述: 商品
   * 1.通过session里面的ticket获取登录用户信息,紧接着判断用户是否存在，若用户不存在则跳转回到登录页面login.
   * 2.添加user用户并传送到前端页面.
   * @param session
   * @param model
   * @param ticket
   * @return
   */
  @RequestMapping("/toList")
  public String toList(HttpSession session, Model model,@CookieValue("userTicket") String ticket) {

    //判断ticket是否为空, 若ticket为空则跳转到login登录页面
    if (StringUtils.isEmpty(ticket)) {
      return "login";
    }

    //通过session里面的ticket获取登录用户,若用户为空则跳转到Login登录页面
    User user = (User) session.getAttribute(ticket);
    if (user == null) {
      return "login";
    }

    //将添加user用户并传送到前端页面
    model.addAttribute("user",user);
    return "goodsList";
  }
}
