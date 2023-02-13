package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.Order;
import com.example.seckill.pojo.User;
import com.example.seckill.vo.GoodsVO;

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
  Order seckill(User user, GoodsVO goods);
}
