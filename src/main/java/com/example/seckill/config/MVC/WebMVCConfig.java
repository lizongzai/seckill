package com.example.seckill.config.MVC;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMVC配置类
 *
 * @author lizongzai
 * @date 2023/02/12 12:17
 * @since 1.0.0
 */

@Configuration
@EnableWebMvc
public class WebMVCConfig implements WebMvcConfigurer {

  @Autowired
  private UserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    //自定义登录用户参数
    resolvers.add(userHandlerMethodArgumentResolver);
  }
}
