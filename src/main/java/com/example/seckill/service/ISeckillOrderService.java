package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.SeckillOrder;
import com.example.seckill.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizongzai
 * @since 2023-02-12
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

  /**
   * 获取秒杀商品结果
   *
   * @param user
   * @param goodsId
   * @return orderId:秒杀商品有结果表示成功, -1:表示秒杀失败, 0: 表示排队中
   */
  Long getResult(User user, Long goodsId);
}
