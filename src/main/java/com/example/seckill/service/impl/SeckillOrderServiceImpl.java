package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.mapper.OrderMapper;
import com.example.seckill.mapper.SeckillOrderMapper;
import com.example.seckill.pojo.SeckillOrder;
import com.example.seckill.pojo.User;
import com.example.seckill.service.ISeckillOrderService;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizongzai
 * @since 2023-02-12
 */
@Service
public class SeckillOrderServiceImpl extends
    ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

  //  @Autowired
  //  private SeckillOrderMapper seckillOrderMapper;
  //  @Autowired
  //  private RedisTemplate redisTemplate;
  //
  //  /**
  //   * 获取秒杀商品结果
  //   *
  //   * @param user
  //   * @param goodsId
  //   * @return orderId:秒杀商品有结果表示成功, -1:表示秒杀失败, 0: 表示排队中
  //   */
  //  @Override
  //  public Long getSeckillResult(User user, Long goodsId) {
  //
  //    //获取秒杀订单结果
  //    SeckillOrder seckillOrder = seckillOrderMapper.selectOne(
  //        new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
  //
  //    //库存不为空
  //    if (null != seckillOrder) {
  //      return seckillOrder.getOrderId();
  //      //库存为空,表示秒杀失败
  //    } else if (redisTemplate.hasKey("isStockeEmpty:" + goodsId)) {
  //      return -1L;
  //    } else {
  //      return 0L;
  //    }
  //  }
  @Autowired
  private SeckillOrderMapper seckillOrderMapper;

  @Autowired
  private RedisTemplate redisTemplate;

  /**
   * 返回值；orderId：成功;-1：秒杀失败;0：排队中
   *
   * @Param: [user, goodsId]
   * @return: java.lang.Long
   * @Date: 2022/8/4
   */
  @Override
  public Long getResult(User user, Long goodsId) {
    SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>().
        eq("user_id", user.getId()).
        eq("goods_id", goodsId)
    );
    if(null != seckillOrder){
      return seckillOrder.getOrderId();
    } else if (redisTemplate.hasKey("isStockEmpty:"+goodsId)) {
      return -1L;
    } else {
      return 0L;
    }
  }
}
