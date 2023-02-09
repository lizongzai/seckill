package com.example.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * 功能描述: MD5工具类
 * 1.工具类中对输入的密码进行两次md5加密，
 * 2.第一次输入密码转为表单密码使用固定盐，
 * 3.第二次表单密码转为存储密码使用自定义盐，对输入密码进行两次加密。
 *
 * @author lizongzai
 * @since 1.0.0
 */

@Component
public class MD5Utils {

  private static final String salt = "1a2b3c4d";

  public static String md5(String src) {
    // 使用DigestUtils这个算法工具类，该类在org.apache.commons.codec.digest这个包目录下
    return DigestUtils.md5Hex(src);
  }

  /**
   * 输入密码转为表单密码
   *
   * @param inputPass
   * @return
   */
  public static String inputPassToFormPass(String inputPass) {
    return md5(salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4));
  }

  /**
   * 表单密码转为存储密码(即数据库密码)
   *
   * @param formPass
   * @param salt
   * @return
   */
  public static String formPassToDBPass(String formPass, String salt) {
    return md5(salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4));
  }

  //后端调用方法
  public static String inputPassToDBPass(String inputPass, String salt) {
    String fromPass = inputPassToFormPass(inputPass);
    String dbPass = formPassToDBPass(fromPass, salt);
    return dbPass;
  }

  public static void main(String[] args) {
    //假设前端输入密为123456,MD5加密之后的密码为ce21b747de5af71ab5c2e20ff0a60eea
    System.out.println(inputPassToFormPass("123456"));
    //表单密码 + 自定义盐salt进行第二次加密, 结果为0687f9701bca74827fcefcd7e743d179
    System.out.println(formPassToDBPass("ce21b747de5af71ab5c2e20ff0a60eea",salt));
    //比较结果
    System.out.println(inputPassToDBPass("123456",salt));
  }

}