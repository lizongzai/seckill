package com.example.seckill.vo;

import com.example.seckill.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 详情返回对象
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {

  private User user;
  private GoodsVO goodsVO;
  private int seckillStatus;
  private int remainSeconds;

}
