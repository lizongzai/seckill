package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.User;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lizongzai
 * @since 2023-02-09
 */
public interface IUserService extends IService<User> {

  /**
   * 登录功能
   *
   * @param loginVo
   * @return
   */
  RespBean doLogin(LoginVo loginVo);
}
