package com.example.seckill.controller;

import com.example.seckill.pojo.User;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IUserService;
import com.example.seckill.vo.DetailVo;
import com.example.seckill.vo.GoodsVO;
import com.example.seckill.vo.RespBean;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;

@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

  @Autowired
  private IUserService userService;
  @Autowired
  private IGoodsService goodsService;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private ThymeleafViewResolver thymeleafViewResolver;

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
  //
  //  //方法三:自定义判断登录用户参数
  //
  //  /**
  //   * 功能描述: 商品 1.通过session里面的ticket获取登录用户信息,改成通过cookie获取用户信息 2.添加user用户并传送到前端页面.
  //   *
  //   * @param model
  //   * @param user
  //   * @return
  //   */
  //  @RequestMapping("/toList")
  //  public String toList(Model model, User user) {
  //
  //    //    //判断ticket是否为空, 若ticket为空则跳转到login登录页面
  //    //    if (StringUtils.isEmpty(ticket)) {
  //    //      return "login";
  //    //    }
  //    //
  //    //    //通过session里面的ticket获取登录用户,若用户为空则跳转到Login登录页面
  //    //    //User user = (User) session.getAttribute(ticket);
  //    //
  //    //    //将session改成从cookie获取用户
  //    //    User user = userService.getUserByCookie(ticket, request, response);
  //    //    if (user == null) {
  //    //      return "login";
  //    //    }
  //
  //    //将添加user用户并传送到前端页面
  //    model.addAttribute("user", user);
  //    model.addAttribute("goodsList", goodsService.findGoodsVo());
  //    return "goodsList";
  //  }

  //方法四:基于自定义判断登录用户参数继承上,使用redis缓存数据库使页面缓存效果

  /**
   * 功能描述: 商品 1.通过session里面的ticket获取登录用户信息,改成通过cookie获取用户信息 2.添加user用户并传送到前端页面.
   *
   * @param model
   * @param user
   * @return
   */
  @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
  @ResponseBody
  public String toList(Model model, User user, HttpServletRequest request,
      HttpServletResponse response) {

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

    //从Redis缓存中获取页面,若页面不为空,则直接返回结果
    ValueOperations valueOperations = redisTemplate.opsForValue();
    String html = (String) valueOperations.get("goodsList");
    if (!StringUtils.isEmpty(html)) {
      return html;
    }

    //将添加user用户并传送到前端页面
    model.addAttribute("user", user);
    model.addAttribute("goodsList", goodsService.findGoodsVo());

    //若页面为空, 手动渲染html并存入reids缓存
    WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
    html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
    if (!StringUtils.isEmpty(html)) {
      //html页面保存在redis为60秒
      valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
    }

    return html;
  }

  /**
   * 跳转商品详情
   *
   * @param model
   * @param user
   * @param goodsId
   * @return
   */
  @RequestMapping(value = "/toDetail2/{goodsId}",produces = "text/html;charset=utf-8")
  @ResponseBody
  public String toDetails2(Model model, User user, @PathVariable Long goodsId,
      HttpServletRequest request, HttpServletResponse response) {
    //从Redis缓存中后去页面,若商品页面不为空, 则直接返回页面
    ValueOperations valueOperations = redisTemplate.opsForValue();
    String html = (String) valueOperations.get("goodsDetail:" + goodsId);
    if (!StringUtils.isEmpty(html)) {
      return html;
    }

    //添加user用并且传输到前端
    model.addAttribute("user", user);

    //获取秒杀商品信息
    GoodsVO goodsVO = goodsService.findGoodsByGoodsId(goodsId);
    model.addAttribute("goods", goodsVO);

    //通过秒杀商品开始和结束时间，判断秒杀状态
    Date startDate = goodsVO.getStartDate();
    Date endDate = goodsVO.getEndDate();
    Date nowDate = new Date();

    //秒杀状态
    int seckillStatus = 0;
    //秒杀倒计时
    int remainSeconds = 0;

    if (nowDate.before(startDate)) {
      //秒杀倒计时
      seckillStatus = 0;
      remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
      System.out.println("秒杀倒计时 = " + remainSeconds);
    } else if (nowDate.after(endDate)) {
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

    //若商品页面为空, 手动渲染商品html并存入reids缓存
    WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
    html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail",webContext);
    if (!StringUtils.isEmpty(html)){
      valueOperations.set("goodsDetail:" + goodsId,html,60,TimeUnit.SECONDS);
    }

    return html;
  }



  /**
   * 跳转商品详情
   *
   * @param model
   * @param user
   * @param goodsId
   * @return
   */
  @RequestMapping("/detail/{goodsId}")
  @ResponseBody
  public RespBean detail(Model model, User user, @PathVariable Long goodsId) {

    //获取秒杀商品信息
    GoodsVO goodsVO = goodsService.findGoodsByGoodsId(goodsId);
    //通过秒杀商品开始和结束时间，判断秒杀状态
    Date startDate = goodsVO.getStartDate();
    Date endDate = goodsVO.getEndDate();
    Date nowDate = new Date();

    //秒杀状态
    int seckillStatus = 0;
    //秒杀倒计时
    int remainSeconds = 0;

    if (nowDate.before(startDate)) {
      //秒杀倒计时
      seckillStatus = 0;
      remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
      System.out.println("秒杀倒计时 = " + remainSeconds);
    } else if (nowDate.after(endDate)) {
      //秒杀已经结束
      seckillStatus = 2;
      remainSeconds = -1;
    } else {
      //秒杀进行中
      seckillStatus = 1;
      remainSeconds = 0;
    }

    DetailVo detailVo = new DetailVo();
    detailVo.setUser(user);
    detailVo.setGoodsVO(goodsVO);
    detailVo.setSeckillStatus(seckillStatus);
    detailVo.setRemainSeconds(remainSeconds);
    System.out.println("aaaaaaaaaaaa = " + detailVo);

    return RespBean.success(detailVo);
  }



}
