package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.config.exception.GlobalException;
import com.example.seckill.mapper.GoodsMapper;
import com.example.seckill.mapper.OrderMapper;
import com.example.seckill.mapper.SeckillGoodsMapper;
import com.example.seckill.pojo.Order;
import com.example.seckill.pojo.SeckillGoods;
import com.example.seckill.pojo.SeckillOrder;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IOrderService;
import com.example.seckill.service.ISeckillGoodsService;
import com.example.seckill.service.ISeckillOrderService;
import com.example.seckill.vo.GoodsVo;
import com.example.seckill.vo.OrderDetailVo;
import com.example.seckill.vo.RespBeanEnum;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private SeckillGoodsMapper seckillGoodsMapper;
  @Autowired
  private OrderMapper orderMapper;
  @Autowired
  private ISeckillOrderService seckillOrderService;
  @Autowired
  private GoodsMapper goodsMapper;
  @Autowired
  private IGoodsService goodsService;
  @Autowired
  private RedisTemplate redisTemplate;

  /**
   * 功能描述: 秒杀商品 1.解决库存商品超卖问题 2.将秒杀订单信息存放在redis缓存中
   *
   * @param user
   * @param goods
   * @return
   */
  @Override
  @Transactional
  public Order seckill(User user, GoodsVo goods) {

    //redis缓存数据库
    ValueOperations valueOperations = redisTemplate.opsForValue();
    //秒杀商品表减库存
    SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
    //秒杀商品表库存减去“1”
    seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
    boolean seckillGoodsResult = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>().
        setSql("stock_count = stock_count -1"). //库存商品减去“-1”
        eq("goods_id", goods.getId()). //根据商品goodsId更新
        gt("stock_count", 0)); //满足条件库存大于0

    //判断是否有库存
    if (seckillGoods.getStockCount() < 1) {
      valueOperations.set("isStockEmpty:" + goods.getId(), "0");
      return null;
    }

    //生成订单
    Order order = new Order();
    order.setUserId(user.getId());
    order.setGoodsId(goods.getId());
    order.setAddrId(0L);
    order.setGoodsName(goods.getGoodsName());
    order.setGoodsCount(1);
    order.setGoodsPrice(goods.getSeckillPrice());
    order.setOrderChannel(1);
    order.setStatus(0);
    order.setCreateDate(new Date());
    orderMapper.insert(order);
    //System.out.println("生成订单 = " + order);

    //生成秒杀订单
    SeckillOrder seckillOrder = new SeckillOrder();
    seckillOrder.setUserId(user.getId());
    seckillOrder.setOrderId(order.getId());
    seckillOrder.setGoodsId(goods.getId());
    seckillOrderService.save(seckillOrder);
    //System.out.println("生成秒杀订单" + seckillOrder);

    redisTemplate.opsForValue().set("order" + user.getId() + ":" + goods.getId(), seckillOrder);
    return order;
  }

  /**
   * 订单详情
   *
   * @param orderId
   * @return
   */
  @Override
  public OrderDetailVo detail(Long orderId) {

    //判断订单是否存在
    if (orderId == null) {
      throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
    }

    //获取订单
    Order order = orderMapper.selectById(orderId);

    //获取商品返回对象
    GoodsVo goodsVO = goodsService.findGoodsByGoodsId(order.getGoodsId());

    //实例化OrderDetailVo
    OrderDetailVo detail = new OrderDetailVo();
    detail.setOrder(order);
    detail.setGoodsVO(goodsVO);

    //返回订单详情
    return detail;
  }
}
