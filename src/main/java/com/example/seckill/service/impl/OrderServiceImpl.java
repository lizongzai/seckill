package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.example.seckill.vo.GoodsVO;
import com.example.seckill.vo.OrderDetailVo;
import com.example.seckill.vo.RespBeanEnum;
import java.time.LocalDateTime;
import java.util.Date;
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
  private SeckillGoodsMapper seckillGoodsMapper;
  @Autowired
  private OrderMapper orderMapper;
  @Autowired
  private ISeckillOrderService seckillOrderService;
  @Autowired
  private GoodsMapper goodsMapper;
  @Autowired
  private IGoodsService goodsService;

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
    //SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
    SeckillGoods seckillGoods = seckillGoodsMapper.selectOne(
        new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
    //System.out.println("秒杀商品表库存减去 = " + seckillGoods);
    seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
    seckillGoodsService.updateById(seckillGoods);

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
    GoodsVO goodsVO = goodsService.findGoodsByGoodsId(order.getGoodsId());

    //实例化OrderDetailVo
    OrderDetailVo detail = new OrderDetailVo();
    detail.setOrder(order);
    detail.setGoodsVO(goodsVO);

    //返回订单详情
    return detail;
  }
}
