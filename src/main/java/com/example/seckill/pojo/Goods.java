package com.example.seckill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
@TableName("t_goods")
@ApiModel(value = "Goods对象", description = "")
public class Goods implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "商品名称")
  private String goodsName;

  @ApiModelProperty(value = "商品标题")
  private String goodsTitle;

  @ApiModelProperty(value = "商品图片")
  private String goodsImg;

  @ApiModelProperty(value = "商品介绍详情")
  private String goodsDetail;

  @ApiModelProperty(value = "商品单价")
  private BigDecimal goodsPrice;

  @ApiModelProperty(value = "商品库存，-1表示没有限制")
  private Integer goodsStock;

  @ApiModelProperty(value = "商品入库时间")
  private Date createDate;

  @ApiModelProperty(value = "商品更新时间")
  private Date updateDate;

}
