package com.example.seckill.config.accessLimit;

import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.utils.CookieUtil;
import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 功能描述: 访问限制拦截器配置
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

  @Autowired
  private IUserService userService;
  @Autowired
  private RedisTemplate redisTemplate;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    //获取当前用户登录
    User user = getUser(request, response);
    UserContext.setUserHolder(user);

    //判断handler是否需要处理的方法
    if (handler instanceof HandlerMethod) {
      //强制转成HandlerMethod处理方法
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      //获取AccessLimit注解的方法
      AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
      //判断accessLimit是否为空，若为空表示没有accessLimit注解，往下执行跳过这个拦截器
      if (accessLimit == null) {
        return true;
      }
      //若这个注解，获取它注解参数
      int second = accessLimit.second();
      int maxCount = accessLimit.maxCount();
      boolean needLogin = accessLimit.needLogin();

      //接口请求
      String key = request.getRequestURI();
      System.out.println("key = " + key);

      //用户没有登录，所以被拦截掉
      if (needLogin) {
        if (user == null) {
          render(response, RespBeanEnum.USER_NOT_EXIST);
          return false;
        } else {
          key += ":" + user.getId();
        }
      }

      //从redis中读取接口请求key
      Integer count = (Integer) redisTemplate.opsForValue().get(key);
      if (count == null) {
        //将key存入redis中
        redisTemplate.opsForValue().set(key, 1, second, TimeUnit.SECONDS);

        //请求最大次数小于maxCount=5,计数增加到redis中
      }else if (count < maxCount) {
        redisTemplate.opsForValue().increment(key);

        //除了上面情况，则返回提示错误信息
      }else {
        render(response,RespBeanEnum.ACCESS_LIMIT_REACHED);
        return false;
      }
    }

    return true;
  }

  /**
   * 构建返回对象
   *
   * @param response
   * @param respBeanEnum
   */
  private void render(HttpServletResponse response, RespBeanEnum respBeanEnum) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    RespBean respBean = RespBean.error(respBeanEnum);
    out.write(new ObjectMapper().writeValueAsString(respBean));
    out.flush();
    out.close();
  }

  /**
   * 功能描述: 获取当前用户登录
   *
   * @param request
   * @param response
   * @return
   */
  private User getUser(HttpServletRequest request, HttpServletResponse response) {
    String userTicket = CookieUtil.getCookieValue(request, "userTicket");
    if (StringUtils.isEmpty(userTicket)) {
      return null;
    }

    User user = userService.getUserByCookie(userTicket, request, response);
    return user;
  }
}
