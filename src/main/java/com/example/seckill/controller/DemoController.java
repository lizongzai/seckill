package com.example.seckill.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 连接测试
 *
 * @author lizongzai
 * @since 1.0.0
 */
@RestController
@Api(tags = "DemoController")
@RequestMapping("/demo")
public class DemoController {

  /**
   * 功能描述: 测试页面跳转
   *
   * @param model
   * @return
   */
  @ApiOperation(value = "连接测试")
  @RequestMapping(value = "/hello",method = RequestMethod.GET)
  public String hello(Model model) {
    model.addAttribute("name", "xxxx");
    return "hello";
  }

}
