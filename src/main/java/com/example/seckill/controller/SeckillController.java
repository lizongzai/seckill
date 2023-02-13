//package com.example.seckill.controller;
//
//
//import com.example.seckill.pojo.User;
//import com.example.seckill.service.IGoodsService;
//import com.example.seckill.vo.GoodsVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * 秒杀商品接口
// * @author lizongzai
// * @since 1.0.0
// */
//@Configuration
//@RequestMapping("/seckill")
//public class SeckillController {
//
//  @Autowired
//  private IGoodsService goodsService;
//
//  /**
//   * 秒杀商品
//   *
//   * @param model
//   * @param user
//   * @param goodsId
//   * @return
//   */
//  @RequestMapping("doSeckill")
//  public String doSeckill(Model model, User user, Long goodsId) {
//    //判断用户是否为空，若登录用户为空则跳转到登录页面
//    if (user == null) {
//      return "login";
//    }
//    //添加user用户并传输到前端
//    model.addAttribute("user",user);
//    GoodsVO goods = goodsService.findGoodsByGoodsId(goodsId);
//
//
//  }
//
//}
