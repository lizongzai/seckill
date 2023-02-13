package com.example.seckill.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.seckill.pojo.Order;
import com.example.seckill.pojo.SeckillOrder;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IOrderService;
import com.example.seckill.service.ISeckillOrderService;
import com.example.seckill.vo.GoodsVO;
import com.example.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 秒杀商品接口
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Configuration
@RequestMapping("/seckill")
public class SeckillController {

  @Autowired
  private IGoodsService goodsService;
  @Autowired
  private ISeckillOrderService seckillOrderService;
  @Autowired
  private IOrderService orderService;

  /**
   * 秒杀商品
   *
   * @param model
   * @param user
   * @param goodsId
   * @return
   */
  @RequestMapping("doSeckill")
  public String doSeckill(Model model, User user, Long goodsId) {

    //判断用户是否为空，若登录用户为空则跳转到登录页面
    if (user == null) {
      return "login";
    }
    //添加user用户并传输到前端
    model.addAttribute("user", user);
    GoodsVO goods = goodsService.findGoodsByGoodsId(goodsId);

    //判断库存
    if (goods.getStock_count() < 1) {
      model.addAttribute("errmsg", RespBeanEnum.NO_GOODS.getMsg());
      return "secKillFail";
    }

    //判断是否重复抢购商品
    SeckillOrder seckillOrder = seckillOrderService.getOne(
        new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
    if (seckillOrder != null) {
      model.addAttribute("errmsg", RespBeanEnum.REPEATE_MIAOSHA.getMsg());
      return "secKillFail";
    }

    //秒杀商品(即生成订单)
    Order order = orderService.seckill(user, goods);
    model.addAttribute("user", user);
    model.addAttribute("goods", goods);

    //跳转到前端秒杀页面
    return "doSeckill";


  }

}
