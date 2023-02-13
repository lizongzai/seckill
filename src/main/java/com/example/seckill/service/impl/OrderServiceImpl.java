package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.mapper.OrderMapper;
import com.example.seckill.pojo.Order;
import com.example.seckill.pojo.SeckillGoods;
import com.example.seckill.pojo.SeckillOrder;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IOrderService;
import com.example.seckill.service.ISeckillGoodsService;
import com.example.seckill.service.ISeckillOrderService;
import com.example.seckill.vo.GoodsVO;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

  @Autowired
  private ISeckillGoodsService seckillGoodsService;
  @Autowired
  private OrderMapper orderMapper;
  @Autowired
  private ISeckillOrderService seckillOrderService;

  /**
   * 秒杀商品
   *
   * @param user
   * @param goods
   * @return
   */
  @Override
  public Order seckill(User user, GoodsVO goods) {

    //秒杀商品表库存减去“1”
    SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
    seckillGoods.setStock_count(seckillGoods.getStock_count() - 1);
    seckillGoodsService.updateById(seckillGoods);

    //生成订单
    Order order = new Order();
    order.setUser_id(user.getId());
    order.setGoods_id(goods.getId());
    order.setAddr_id(0L);
    order.setGoods_name(goods.getGoods_name());
    order.setGoods_count(1);
    order.setGoods_price(goods.getSeckill_price());
    order.setOrder_channel(0);
    order.setStatus(0);
    order.setCreate_date(LocalDateTime.now());
    orderMapper.insert(order);

    //生成秒杀订单
    SeckillOrder seckillOrder = new SeckillOrder();
    seckillOrder.setUser_id(user.getId());
    seckillOrder.setOrder_id(order.getId());
    seckillOrder.setGoods_id(goods.getId());
    seckillOrderService.save(seckillOrder);

    return order;
  }
}
