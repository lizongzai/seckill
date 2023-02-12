package com.example.seckill.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.seckill.pojo.Goods;
import com.example.seckill.pojo.SeckillGoods;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品返回对象
 *
 * @author lizongzai
 * @since 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVO extends Goods {

  @ApiModelProperty(value = "秒杀商品列表")
  public SeckillGoods seckillGoods;

  @ApiModelProperty(value = "秒杀价")
  private BigDecimal seckill_price;

  @ApiModelProperty(value = "库存数量")
  private Integer stock_count;

  @ApiModelProperty(value = "秒杀开始时间")
  private LocalDateTime start_date;

  @ApiModelProperty(value = "秒杀结束时间")
  private LocalDateTime end_date;

}
