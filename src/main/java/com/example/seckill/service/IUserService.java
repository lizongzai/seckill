package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.User;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
   * @param request
   * @param response
   * @return
   */
  RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

  /**
   * 根据Cookie获取用户
   *
   * @param userTicket
   * @return
   */
  User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);


  /**
   * 更新密码
   *
   * @param userTicket
   * @param password
   * @param request
   * @param response
   * @return
   */
  RespBean updatePassword(String userTicket, String password, HttpServletRequest request,
      HttpServletResponse response);
}
