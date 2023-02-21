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
}
