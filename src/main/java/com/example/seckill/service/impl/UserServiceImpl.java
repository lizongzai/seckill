package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.config.exception.GlobalException;
import com.example.seckill.mapper.UserMapper;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.utils.CookieUtil;
import com.example.seckill.utils.MD5Util;
import com.example.seckill.utils.UUIDUtil;
import com.example.seckill.utils.ValidatorUtil;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizongzai
 * @since 2023-02-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

  @Autowired
  private UserMapper userMapper;
  @Autowired
  private RedisTemplate redisTemplate;

  /**
   * 描述功能: 登录功能 1.原先用户信息存放在Cookie里面，现在改成放入redis里面
   *
   * @param loginVo
   * @param request
   * @param response
   * @return
   */
  @Override
  public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {

    //获取手机号码和密码
    String mobile = loginVo.getMobile();
    String password = loginVo.getPassword();

//    //判断手机号码和密码是否为空
//    if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
//      return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//    }
//
//    //校验手机号码格式
//    if (!ValidatorUtil.isMobile(mobile)) {
//      return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//    }
//
//    //根据手机号码获取用户
//    User user = userMapper.selectById(mobile);
//    if (user == null) {
//      return RespBean.error(RespBeanEnum.USER_NOT_EXIST);
//    }
//
//    //判断密码是否正确
//    if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
//      return RespBean.error(RespBeanEnum.PASSWORD_ERROR);
//    }

    //根据手机号码获取用户
    User user = userMapper.selectById(mobile);
    if (user == null) {
      throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
    }

    //判断密码是否正确
    if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
      throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
    }

    //生成cookie
    String ticket = UUIDUtil.uuid();
    //将ticket和user放入request请求session() 改成下面redis缓存存储
    //request.getSession().setAttribute(ticket,user);
    //将用户信息存入redis中
    redisTemplate.opsForValue().set("user:" + ticket, user);
    CookieUtil.setCookie(request, response, "userTicket", ticket);
    System.out.println("用户登录 uesrTicket = " + ticket);
    return RespBean.success(ticket);
  }

  /**
   * 根据Cookie获取用户
   *
   * @param userTicket
   * @return
   */
  @Override
  public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {

    //判断userTicke是否为空
    if (userTicket == null) {
      return null;
    }

    //根据userTicket获取用户信息
    User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
    //若用户不为空，但是userTicket为空则重置Cookie
    if (user != null) {
      CookieUtil.setCookie(request, response, "userTicket", userTicket);
    }

    return user;
  }
}
