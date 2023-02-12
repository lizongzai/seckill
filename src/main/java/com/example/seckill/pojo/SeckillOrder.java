package com.example.seckill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
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
@TableName("t_seckill_order")
@ApiModel(value="SeckillOrder对象", description="")
public class SeckillOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "秒杀订单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long user_id;

    @ApiModelProperty(value = "订单ID")
    private Long order_id;

    @ApiModelProperty(value = "商品ID")
    private Long goods_id;


}
