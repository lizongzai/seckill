package com.example.seckill.config.MVC;

import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.utils.CookieUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 自定义用户登录参数
 *
 * @author lizongzai
 * @date 2023/02/12 12:17
 * @since 1.0.0
 */

@Component
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  @Autowired
  private IUserService userService;

  /**
   * 功能描述: 判断是否支持对应的参数类型，即参数是否是User类型
   * 1. 该方法就是对参数条件判断，若条件的结果为true,则执行resolveArgument()方法
   *
   * @param parameter the method parameter to check
   * @return
   */
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    //获取参数类型
    Class<?> clazz = parameter.getParameterType();
    //判断参数类型是否为User.class
    return clazz == User.class;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    //获取request
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    //获取response
    HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
    //获取userTicket
    String userTicket = CookieUtil.getCookieValue(request, "userTicket");
    //判断userTicket是否为空
    if (StringUtils.isEmpty(userTicket)) {
      return null;
    }
    //根据Cookie获取用户
    return userService.getUserByCookie(userTicket, request, response);
  }
}
