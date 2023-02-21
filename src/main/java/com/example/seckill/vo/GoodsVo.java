package com.example.seckill.vo;

import com.example.seckill.pojo.Goods;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
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
public class GoodsVo extends Goods {

  @ApiModelProperty(value = "秒杀价")
  private BigDecimal seckillPrice;

  @ApiModelProperty(value = "库存数量")
  private Integer stockCount;

  @ApiModelProperty(value = "秒杀开始时间")
  private Date startDate;

  @ApiModelProperty(value = "秒杀结束时间")
  private Date endDate;

}
