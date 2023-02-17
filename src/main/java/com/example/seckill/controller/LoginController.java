package com.example.seckill.controller;

import com.example.seckill.service.IUserService;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录接口
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

  @Autowired
  private IUserService userService;

  /**
   * 跳转登录页面
   *
   * @return
   */
  @RequestMapping("/toLogin")
  public String toLogin() {
    return "login";
  }

  /**
   * 登录功能
   *
   * @param loginVo
   * @return
   */
  @RequestMapping("/doLogin")
  @ResponseBody
  public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
    return userService.doLogin(loginVo, request, response);
  }
}
