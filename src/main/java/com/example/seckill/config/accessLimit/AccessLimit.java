package com.example.seckill.config.accessLimit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述: 限制访问配置类
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

  //访问时间
  int second();

  //最大访问次数
  int maxCount();

  //用户登录标记
  boolean needLogin();
}