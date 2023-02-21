package com.example.seckill.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.seckill.config.rabbitMQ.MQSeckillSender;
import com.example.seckill.pojo.Order;
import com.example.seckill.pojo.SeckillMessage;
import com.example.seckill.pojo.SeckillOrder;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IOrderService;
import com.example.seckill.service.ISeckillOrderService;
import com.example.seckill.utils.JsonUtil;
import com.example.seckill.vo.GoodsVo;
import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 秒杀商品接口
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

  @Autowired
  private IGoodsService goodsService;
  @Autowired
  private ISeckillOrderService seckillOrderService;
  @Autowired
  private IOrderService orderService;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private MQSeckillSender mqSeckillSender;
  private Map<Long, Boolean> EmptyStockMap = new HashMap<>();

  /**
   * 功能描述:秒杀商品(第一阶段)
   * 1.判断用户是否为空，若登录用户为空则跳转到登录页面
   * 2.判断库存是否足够，库存不足无法参与秒杀活动
   * 3.判断是否重复抢购商品即该商品每人限购一件
   * 4.生成秒杀商品(即生成订单表、秒杀订单表)，秒杀商品表库存减去“1”
   *
   * @param model
   * @param user
   * @param goodsId
   * @return
   */
  @RequestMapping("/doSeckill2")
  public String doSeckill2(Model model, User user, Long goodsId) {

    //判断用户是否为空，若登录用户为空则跳转到登录页面
    if (user == null) {
      return "login";
    }

    //添加user用户并传输到前端
    model.addAttribute("user", user);

    GoodsVo goods = goodsService.findGoodsByGoodsId(goodsId);
    //System.out.println("添加user用户并传输到前端 = " + goods);
    //判断库存是否足够
    if (goods.getStockCount() < 1) {
      model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
      return "secKillFail";
    }

    //判断是否重复抢购商品
    SeckillOrder seckillOrder = seckillOrderService.getOne(
        new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
    //System.out.println("判断是否重复抢购商品 = " + seckillOrder);
    if (seckillOrder != null) {
      model.addAttribute("errmsg", RespBeanEnum.REPEATE_MIAOSHA.getMessage());
      return "secKillFail";
    }

    //秒杀商品(即生成订单)
    Order order = orderService.seckill(user, goods);
    //System.out.println("秒杀商品 = " + order);
    model.addAttribute("user", user);
    model.addAttribute("goods", goods);
    model.addAttribute("order", order);

    //跳转到前端秒杀页面
    return "orderDetail";
  }

  /**
   * 功能描述:秒杀商品(第二阶段优化)
   * 1.判断用户是否为空，若登录用户为空则返回用户不存在
   * 2.判断库存是否足够，库存不足无法参与秒杀活动
   * 3.判断是否重复抢购商品即该商品每人限购一件
   * 4.生成秒杀商品(即生成订单表、秒杀订单表)，秒杀商品表库存减去“1”
   * 5.将上面（4.点）将从数据库获取抢购商品改成通过redis缓存中获取抢购商品(即秒杀商品)
   *
   * @param model
   * @param user
   * @param goodsId
   * @return
   */
  @RequestMapping(value = "/doSeckill3", method = RequestMethod.POST)
  @ResponseBody
  public RespBean doSeckill3(Model model, User user, Long goodsId) {

    //若登录用户为空则返回用户不存在
    if (user == null) {
      return RespBean.error(RespBeanEnum.USER_NOT_EXIST);
    }

    GoodsVo goods = goodsService.findGoodsByGoodsId(goodsId);
    //判断库存是否足够
    if (goods.getStockCount() < 1) {
      model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
      return RespBean.error(RespBeanEnum.EMPTY_STOCK);
    }

    //判断是否重复抢购商品(优化从redis缓存中获取)
    //SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));

    //将从数据库获取抢购商品改成通过redis缓存中获取抢购商品(即秒杀商品)
    SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue()
        .get("order:" + user.getId() + ":" + goodsId);

    if (seckillOrder != null) {
      model.addAttribute("errmsg", RespBeanEnum.REPEATE_MIAOSHA.getMessage());
      return RespBean.error(RespBeanEnum.REPEATE_MIAOSHA);
    }

    //秒杀商品(即生成订单)
    Order order = orderService.seckill(user, goods);
    return RespBean.success(order);
  }


  /**
   * 功能描述:秒杀商品(第三阶段优化)
   * 1.现阶段优化的目的是减少数据库访问,提高秒杀商品吞吐率
   * 2.通过在redis缓存数据库中预减库存操作(前提是通过InitializingBean()预加载商品到redis数据库中)
   * 3.RabbitMQ消息队列处理异步数据库操作
   * 4.封装SeckillMessage秒杀消息对象
   * 5.通过RabbtiMQ发送秒杀商品消息，紧接着消息接收者中做了实际秒杀操作动作
   *    5.1 判断商品库存是否为空
   *    5.2 判断是否重复抢购商品
   *    5.3 下单操作
   *
   * @param model
   * @param user
   * @param goodsId
   * @return
   */
  @RequestMapping(value = "/{path}/doSeckill", method = RequestMethod.POST)
  @ResponseBody
  public RespBean doSeckill(@PathVariable String path, User user, Long goodsId) {

    //判断用户是否为空
    if (user == null) {
      return RespBean.error(RespBeanEnum.USER_NOT_EXIST);
    }

    //获取redis缓存
    ValueOperations valueOperations = redisTemplate.opsForValue();

    //检验秒杀地址
    boolean check = orderService.checkPath(path,user,goodsId);
    if (!check) {
      return RespBean.error(RespBeanEnum.REQUEST_ILLEGAL);
    }

    //判断是否重复抢购商品
    SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue()
        .get("order:" + user.getId() + ":" + goodsId);
    if (seckillOrder != null) {
      return RespBean.error(RespBeanEnum.REPEATE_MIAOSHA);
    }

    //通过内存标记,减少Redis的访问次数
    if (EmptyStockMap.get(goodsId)) {
      return RespBean.error(RespBeanEnum.EMPTY_STOCK);
    }

    //预减库存操作
    Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
    if (stock < 0) {
      //鉴于库存的数量不能为负数(即"-1"),所以再次加”1“,使库存的数量为0
      valueOperations.increment("seckillGoods:" + goodsId);
      //通过内存标记，true表示redis缓存中的库存为空
      EmptyStockMap.put(goodsId, true);
      return RespBean.error(RespBeanEnum.EMPTY_STOCK);
    }

    //实例化秒杀信息对象
    SeckillMessage seckillMessage = new SeckillMessage(user, goodsId);
    //发送：秒杀商品(即生成订单)
    mqSeckillSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));
    return RespBean.success(0);
  }

  /**
   * 系统初始化,将商品库存数量预加载到redis缓存中
   *
   * @throws Exception
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    List<GoodsVo> list = goodsService.findGoodsVo();
    if (CollectionUtils.isEmpty(list)) {
      return;
    }

    list.forEach(goodsVo -> {
      //库存数量预加载到redis中
      redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
      //通过内存标记，false表示redis缓存中是有库存
      EmptyStockMap.put(goodsVo.getId(), false);
    });
  }

  /**
   * 获取秒杀商品结果
   *
   * @param user
   * @param goodsId
   * @return orderId:秒杀商品有结果表示成功, -1:表示秒杀失败, 0: 表示排队中
   */
  @RequestMapping(value = "/result", method = RequestMethod.GET)
  @ResponseBody
  public RespBean getResult(User user, Long goodsId) {
    if (user == null) {
      return RespBean.error(RespBeanEnum.USER_NOT_EXIST);
    }
    Long orderId = seckillOrderService.getResult(user, goodsId);
    return RespBean.success(orderId);
  }

  /**
   * 功能描述: 获取秒杀地址
   *
   * @param user
   * @param goodsId
   * @return
   */
  @RequestMapping(value = "/path", method = RequestMethod.GET)
  @ResponseBody
  public RespBean getSeckillPath(User user, Long goodsId) {
    if (user == null) {
      return RespBean.error(RespBeanEnum.USER_NOT_EXIST);
    }
    //返回加密后的uuid
    String uuid = orderService.createPath(user, goodsId);
    return RespBean.success(uuid);
  }
}
