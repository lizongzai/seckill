package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.config.exception.GlobalException;
import com.example.seckill.mapper.UserMapper;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.utils.MD5Util;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
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

  /**
   * 登录功能
   *
   * @param loginVo
   * @return
   */
  @Override
  public RespBean doLogin(LoginVo loginVo) {

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

//    //根据手机号码获取用户
//    User user = userMapper.selectById(mobile);
//    if (user == null) {
//      return RespBean.error(RespBeanEnum.USER_NOT_EXIST);
//    }

//    //判断密码是否正确
//    if (!MD5Utils.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
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


    return RespBean.success();
  }
}
