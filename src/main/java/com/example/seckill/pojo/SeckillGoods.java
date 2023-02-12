package com.example.seckill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizongzai
 * @since 2023-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_seckill_goods")
@ApiModel(value="SeckillGoods对象", description="")
public class SeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "秒杀商品ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品ID")
    private Long goods_id;

    @ApiModelProperty(value = "秒杀价")
    private BigDecimal seckill_price;

    @ApiModelProperty(value = "库存数量")
    private Integer stock_count;

    @ApiModelProperty(value = "秒杀开始时间")
    private LocalDateTime start_date;

    @ApiModelProperty(value = "秒杀结束时间")
    private LocalDateTime end_date;


}
