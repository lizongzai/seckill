package com.example.seckill.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author lizongzai
 * @since 1.0.0
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

  /**
   * 功能描述: 测试页面跳转
   *
   * @param model
   * @return
   */
  @RequestMapping("/hello")
  public String hello(Model model) {
    model.addAttribute("name", "xxxx");
    return "hello";
  }

}
