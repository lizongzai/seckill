package com.example.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 公共返回对象枚举类
 *
 * @author lizongzai
 * @since 1.0.0
 */

public class RespBeanEnum {

  //状态码
  private int code;
  //返回消息
  private String msg;

  //通用的错误码
  public static RespBeanEnum SUCCESS = new RespBeanEnum(200, "success");
  public static RespBeanEnum SERVER_ERROR = new RespBeanEnum(500, "服务端异常");
  public static RespBeanEnum BIND_ERROR = new RespBeanEnum(500101, "参数校验异常：%s");
  public static RespBeanEnum REQUEST_ILLEGAL = new RespBeanEnum(500102, "请求非法");
  public static RespBeanEnum ACCESS_LIMIT_REACHED= new RespBeanEnum(500104, "访问太频繁！");

  //登录模块 5002XX
  public static RespBeanEnum SESSION_ERROR = new RespBeanEnum(500210, "Session不存在或者已经失效");
  public static RespBeanEnum PASSWORD_EMPTY = new RespBeanEnum(500211, "登录密码不能为空");
  public static RespBeanEnum MOBILE_EMPTY = new RespBeanEnum(500212, "手机号不能为空");
  public static RespBeanEnum MOBILE_ERROR = new RespBeanEnum(500213, "手机号格式错误");
  public static RespBeanEnum MOBILE_NOT_EXIST = new RespBeanEnum(500214, "手机号不存在");
  public static RespBeanEnum PASSWORD_ERROR = new RespBeanEnum(500215, "密码错误");
  public static RespBeanEnum LOGIN_ERROR = new RespBeanEnum(500210, "用户名或密码不正确");

  public static RespBeanEnum USER_NO_LOGIN = new RespBeanEnum(500216, "用户未登录");
  public static RespBeanEnum USER_NOT_EXIST = new RespBeanEnum(500217, "用户不存在");
  //商品模块 5003XX
  public static RespBeanEnum NO_GOODS = new RespBeanEnum(500100, "商品库存不足");
  //订单模块 5004XX
  public static RespBeanEnum ORDER_NOT_EXIST = new RespBeanEnum(500400, "订单不存在");
  //秒杀模块 5005XX
  public static RespBeanEnum MIAO_SHA_OVER = new RespBeanEnum(500500, "商品已经秒杀完毕");
  public static RespBeanEnum REPEATE_MIAOSHA = new RespBeanEnum(500501, "该商品每人限购一件");
  private RespBeanEnum( ) {
  }

  private RespBeanEnum( int code,String msg ) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }
  public void setCode(int code) {
    this.code = code;
  }
  public String getMsg() {
    return msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }

  public RespBeanEnum fillArgs(Object... args) {
    int code = this.code;
    String message = String.format(this.msg, args);
    return new RespBeanEnum(code, message);
  }

  @Override
  public String toString() {
    return "CodeMsg [code=" + code + ", msg=" + msg + "]";
  }

}
