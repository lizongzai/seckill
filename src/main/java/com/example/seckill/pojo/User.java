package com.example.seckill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author lizongzai
 * @since 2023-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户ID, 手机号码")
  private Long id;

  @ApiModelProperty(value = "昵称")
  private String nickname;

  @ApiModelProperty(value = "MD5(MD5(pass明文+固定salt)+salt)")
  private String password;

  @ApiModelProperty(value = "固定salt")
  private String salt;

  @ApiModelProperty(value = "头像")
  private String head;

  @ApiModelProperty(value = "注册时间")
  private LocalDateTime registerDate;

  @ApiModelProperty(value = "最后一次登录时间")
  private LocalDateTime lastLoginDate;

  @ApiModelProperty(value = "登录次数")
  private Integer loginCount;


}
