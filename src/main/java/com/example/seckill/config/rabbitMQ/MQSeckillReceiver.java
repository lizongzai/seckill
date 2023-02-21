package com.example.seckill.config.rabbitMQ;

import com.example.seckill.pojo.SeckillMessage;
import com.example.seckill.pojo.SeckillOrder;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IOrderService;
import com.example.seckill.utils.JsonUtil;
import com.example.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息接收者(或消息消费者)
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Service
@Slf4j
public class MQSeckillReceiver {

  @Autowired
  private IGoodsService goodsService;
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private IOrderService orderService;

  @RabbitListener(queues = "seckillQueue")
  public void receiveSeckillMessage(String message) {

    //打印输出
    log.info("接收消息: " + message);

    //将Json转成Object对象
    SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(message, SeckillMessage.class);
    Long goodsId = seckillMessage.getGoodsId();
    User user = seckillMessage.getUser();

    //判断商品库存是否为空
    GoodsVo goodsVo = goodsService.findGoodsByGoodsId(goodsId);
    System.out.println("判断商品库存是否为空 = " + goodsVo);
    if (goodsVo.getStockCount() < 1) {
      return;
    }

    //判断是否重复抢购商品
    SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
    System.out.println("判断是否重复抢购商品 = " + seckillOrder);
    if (seckillOrder != null) {
      return;
    }

    //下单操作
    orderService.seckill(user, goodsVo);
  }
}
