package com.example.seckill.controller;

import com.example.seckill.pojo.User;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IUserService;
import com.example.seckill.vo.GoodsVO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
   * 功能描述: 商品 1.通过session里面的ticket获取登录用户信息,改成通过cookie获取用户信息 2.添加user用户并传送到前端页面.
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
    model.addAttribute("goodsList", goodsService.findGoodsVo());
    return "goodsList";
  }

  /**
   * 跳转商品详情
   *
   * @param model
   * @param user
   * @param goodsId
   * @return
   */
  @RequestMapping("/toDetail/{goodsId}")
  public String toDetails(Model model, User user, @PathVariable Long goodsId) {

    //添加user用并且传输到前端
    model.addAttribute("user", user);

    //获取秒杀商品信息
    GoodsVO goodsVO = goodsService.findGoodsByGoodsId(goodsId);
    model.addAttribute("goods", goodsVO);

    //通过秒杀商品开始和结束时间，判断秒杀状态
    LocalDateTime start_date = goodsVO.getStart_date();
    LocalDateTime end_date = goodsVO.getEnd_date();
    LocalDateTime now_date = LocalDateTime.now();

    //LocalDateTime转Date
    Date nowDate = Date.from( now_date.atZone( ZoneId.systemDefault()).toInstant());
    Date startDate = Date.from( start_date.atZone( ZoneId.systemDefault()).toInstant());
    Date endDate = Date.from( end_date.atZone( ZoneId.systemDefault()).toInstant());

    //秒杀状态
    int seckillStatus = 0;
    //秒杀倒计时
    long remainSeconds = 0;

    if (now_date.isBefore(start_date)) {
      //秒杀倒计时
      seckillStatus = 0;
      remainSeconds = ((startDate.getTime() - nowDate.getTime())/1000);
      System.out.println("秒杀倒计时 = " + remainSeconds);
    } else if (now_date.isAfter(end_date)) {
      //秒杀已经结束
      seckillStatus = 2;
      remainSeconds = -1;
    } else {
      //秒杀进行中
      seckillStatus = 1;
      remainSeconds = 0;
    }
    model.addAttribute("seckillStatus", seckillStatus);
    model.addAttribute("remainSeconds", remainSeconds);

    return "goodsDetail";
  }
}
