package com.example.seckill.utils;

import java.util.UUID;

/**
 * 生成UUID的工具类
 *
 * @author lizongzai
 * @date 2023/02/10 15:52
 * @since 1.0.0
 */
public class UUIDUtil {

  /**
   * 产生UUID字符串，将“-”去除
   *
   * @return
   */
  public static String uuid() {
    return UUID.randomUUID().toString().replace("-", "");
  }

}
