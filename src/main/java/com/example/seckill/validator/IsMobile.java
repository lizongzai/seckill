package com.example.seckill.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.seckill.vo.IsMobileValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 验证手机号注解
 *
 * @author lizongzai
 * @since 1.0.0
 */

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class}) //在这里填写自定义校验规则类IsMobileValidator.class
public @interface IsMobile {

  //手机号码必填
  boolean required() default true;

  //默认返回信息
  String message() default "手机号码格式错误";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
