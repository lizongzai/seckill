package com.example.seckill.config.MVC;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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

  /**
   * Spring MVC的参数解析器，用于添加自定义参数解析器
   *
   * @param resolvers
   */
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    //自定义登录用户参数
    resolvers.add(userHandlerMethodArgumentResolver);
  }

  /**
   * 自定义静态资源映射目录
   *
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //url访问路径
    //映射项目中真实路径“/static”,最后面必须加"/"，即可获取当前项目路径
    registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
  }
}
