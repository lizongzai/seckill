package com.example.seckill.vo;

import com.example.seckill.validator.IsMobile;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 登录参数
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Data
public class LoginVo {
  //手机号码
  @NotNull
  @IsMobile
  private String mobile;
  //密码
  @NotNull
  @Length(min = 32)
  private String password;
}
