package com.example.seckill.controller;

import com.example.seckill.pojo.Goods;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IUserService;
import com.example.seckill.vo.GoodsVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

  @Autowired
  private IUserService userService;
  @Autowired
  private IGoodsService goodsService;

    // 方法一：使用session获取用户登录
    //  /**
    //   * 功能描述: 商品
    //   * 1.通过session里面的ticket获取登录用户信息,紧接着判断用户是否存在，若用户不存在则跳转回到登录页面login.
    //   * 2.改成通过cookie获取用户信息
    //   * 3.添加user用户并传送到前端页面.
    //   * @param session
    //   * @param model
    //   * @param ticket
    //   * @return
    //   */
    //  @RequestMapping("/toList")
    //  public String toList(HttpSession session, Model model,@CookieValue("userTicket") String ticket) {
    //
    //    //判断ticket是否为空, 若ticket为空则跳转到login登录页面
    //    if (StringUtils.isEmpty(ticket)) {
    //      return "login";
    //    }
    //
    //    //通过session里面的ticket获取登录用户,若用户为空则跳转到Login登录页面
    //    User user = (User) session.getAttribute(ticket);
    //    if (user == null) {
    //      return "login";
    //    }
    //
    //    //将添加user用户并传送到前端页面
    //    model.addAttribute("user",user);
    //    return "goodsList";
    //  }

    // 方法二：通过Redis存储用户信息，cookies获取用户信息
    //  /**
    //   * 功能描述: 商品
    //   * 1.通过session里面的ticket获取登录用户信息,改成通过cookie获取用户信息
    //   * 2.添加user用户并传送到前端页面.
    //   *
    //   * @param request
    //   * @param response
    //   * @param model
    //   * @param ticket
    //   * @return
    //   */
    //  @RequestMapping("/toList")
    //  public String toList(HttpServletRequest request, HttpServletResponse response, Model model,
    //      @CookieValue("userTicket") String ticket) {
    //
    //    //判断ticket是否为空, 若ticket为空则跳转到login登录页面
    //    if (StringUtils.isEmpty(ticket)) {
    //      return "login";
    //    }
    //
    //    //通过session里面的ticket获取登录用户,若用户为空则跳转到Login登录页面
    //    //User user = (User) session.getAttribute(ticket);
    //
    //    //将session改成从cookie获取用户
    //    User user = userService.getUserByCookie(ticket, request, response);
    //    if (user == null) {
    //      return "login";
    //    }
    //
    //    //将添加user用户并传送到前端页面
    //    model.addAttribute("user", user);
    //    return "goodsList";
    //  }


  //方法三:自定义判断登录用户参数
  /**
   * 功能描述: 商品
   * 1.通过session里面的ticket获取登录用户信息,改成通过cookie获取用户信息
   * 2.添加user用户并传送到前端页面.
   *
   * @param model
   * @param user
   * @return
   */
  @RequestMapping("/toList")
  public String toList(Model model, User user) {

    //    //判断ticket是否为空, 若ticket为空则跳转到login登录页面
    //    if (StringUtils.isEmpty(ticket)) {
    //      return "login";
    //    }
    //
    //    //通过session里面的ticket获取登录用户,若用户为空则跳转到Login登录页面
    //    //User user = (User) session.getAttribute(ticket);
    //
    //    //将session改成从cookie获取用户
    //    User user = userService.getUserByCookie(ticket, request, response);
    //    if (user == null) {
    //      return "login";
    //    }

    //将添加user用户并传送到前端页面
    model.addAttribute("user", user);
    model.addAttribute("goodsList",goodsService.findGoodsVo());
    return "goodsList";
  }
}
