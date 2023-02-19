package com.example.seckill.vo;

import com.example.seckill.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: 订单详情公共返回对象,仅供前端展示使用
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {
  private GoodsVO goodsVO;
  private Order order;
}
