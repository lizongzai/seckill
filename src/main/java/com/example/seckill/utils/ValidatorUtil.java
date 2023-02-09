package com.example.seckill.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

/**
 * 手机号码验证
 *
 * @author lizongzai
 * @since 1.0.0
 */
public class ValidatorUtil {

  private static final Pattern mobile_pattern = Pattern.compile("[1]([3-9])[0-9]{9}$");

  public static boolean isMobile(String mobile) {
    if (StringUtils.isEmpty(mobile)) {
      return false;
    }

    Matcher matcher = mobile_pattern.matcher(mobile);
    return matcher.matches();
  }

}
