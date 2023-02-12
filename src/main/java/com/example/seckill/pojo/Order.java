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
@TableName("t_order")
@ApiModel(value="Order对象", description="")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long user_id;

    @ApiModelProperty(value = "商品id")
    private Long goods_id;

    @ApiModelProperty(value = "收货地址id")
    private Long addr_id;

    @ApiModelProperty(value = "冗余过来的商品名称")
    private String goods_name;

    @ApiModelProperty(value = "商品数量")
    private Integer goods_count;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal goods_price;

    @ApiModelProperty(value = "支付通道：1 PC、2 Android、3 ios")
    private Integer order_channel;

    @ApiModelProperty(value = "订单状态：0 未支付，1已支付，2 已发货，3 已收货，4 已退款，‘5 已完成")
    private Integer status;

    private LocalDateTime create_date;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime pay_date;


}
