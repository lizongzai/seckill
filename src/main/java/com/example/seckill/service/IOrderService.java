package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.Order;
import com.example.seckill.pojo.User;
import com.example.seckill.vo.GoodsVo;
import com.example.seckill.vo.OrderDetailVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lizongzai
 * @since 2023-02-12
 */
public interface IOrderService extends IService<Order> {

  /**
   * 秒杀商品
   *
   * @param user
   * @param goods
   * @return
   */
  Order seckill(User user, GoodsVo goods);

  /**
   * 订单详情
   *
   * @param orderId
   * @return
   */
  OrderDetailVo detail(Long orderId);

  /**
   * 功能描述: 获取秒杀地址
   *
   * @param user
   * @param goodsId
   * @return
   */
  String createPath(User user, Long goodsId);

  /**
   * 功能描述: 检验秒杀地址
   *
   * @param path
   * @param user
   * @param goodsId
   * @return
   */
  boolean checkPath(String path, User user, Long goodsId);
}
