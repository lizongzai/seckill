package com.example.seckill.vo;

import com.example.seckill.utils.ValidatorUtil;
import com.example.seckill.validator.IsMobile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

/**
 * 手机号码校验规则
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

  //手机号码默认值为false
  private boolean required = false;

  @Override
  public void initialize(IsMobile constraintAnnotation) {
    //首先获取是否是必填
    constraintAnnotation.required();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    //判断手机号码是否非必填
    if (required) {
      //若传入的手机号码不为空,则进行手机号码校验
      return ValidatorUtil.isMobile(value);
    } else {
      //判断手机是否为空,若传入的手机号码为空,则返回true必填.若传入的手机号码不为空,则进行手机号码校验.
      if (StringUtils.isEmpty(value)) {
        return true;
      } else {
        return ValidatorUtil.isMobile(value);
      }
    }
  }
}
