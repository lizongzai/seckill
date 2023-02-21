package com.example.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述: 秒杀信息
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {
  private User user;
  private Long goodsId;
}
