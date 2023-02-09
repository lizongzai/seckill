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

  //固定Salt
  private static final String salt = "1a2b3c4d";

  //使用DigestUtils这个算法工具类，该类在org.apache.commons.codec.digest这个包目录下
  public static String md5(String src) {
    return DigestUtils.md5Hex(src);
  }

  /**
   * 输入密码转为表单密码
   *
   * @param inputPass
   * @return
   */
  public static String inputPassToFormPass(String inputPass) {
    return md5("" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4));
  }

  /**
   * 表单密码转为存储密码(即数据库密码)
   *
   * @param formPass
   * @param salt
   * @return
   */
  public static String formPassToDBPass(String formPass, String salt) {
    return md5("" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4));
  }

  //后端调用方法
  public static String inputPassToDBPass(String inputPass, String salt) {
    String fromPass = inputPassToFormPass(inputPass);
    String dbPass = formPassToDBPass(fromPass, salt);
    return dbPass;
  }

  //用户端MD5加密是为了防止用户密码在网络中明文传输泄密，服务端MD5加密是为了提高密码安全性起到双重保险
  public static void main(String[] args) {
    //假设前端输入密为123456,MD5加密之后的密码为 d3b1294a61a07da9b49b6e22b2cbd7f9
    System.out.println(inputPassToFormPass("123456"));
    //表单密码 + 自定义盐salt进行第二次加密, 结果为 68586326b7f2e74df8c0d858cb24647f
    System.out.println(formPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9",salt));
    //比较结果
    System.out.println(inputPassToDBPass("123456",salt));
  }

}
