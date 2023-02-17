//package com.example.seckill.vo;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * 公共返回对象
// *
// * @author lizongzai
// * @since 1.0.0
// */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class RespBean {
//
//  //状态码
//  private long code;
//  //返回消息
//  private String message;
//  //返回对象
//  private Object object;
//
//  /**
//   * 成功返回结果
//   *
//   * @return
//   */
//  public static RespBean success() {
//    return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMsg(), null);
//  }
//
//  /**
//   * 成功返回结果
//   *
//   * @return
//   */
//  public static RespBean success(Object object) {
//    return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMsg(), object);
//  }
//
//  /**
//   * 失败返回结果
//   *
//   * @param respBeanEnum
//   * @return
//   */
//  public static RespBean error(RespBeanEnum respBeanEnum) {
//    return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMsg(), null);
//  }
//
//  /**
//   * 失败返回结果
//   *
//   * @param respBeanEnum
//   * @return
//   */
//  public static RespBean error(RespBeanEnum respBeanEnum, Object object) {
//    return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMsg(),object);
//  }
//
//}


package com.example.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 公共返回对象
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

  //状态码
  private long code;
  //返回消息
  private String message;
  //返回对象
  private Object obj;

  /**
   * 成功返回结果
   */
  public static RespBean success() {
    return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), null);
  }

  /**
   * 成功返回结果
   *
   * @param obj
   */
  public static RespBean success(Object obj) {
    return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), obj);
  }

  /**
   * 失败返回结果
   *
   * @param respBeanEnum
   * @return
   */
  public static RespBean error(RespBeanEnum respBeanEnum) {
    return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
  }
}