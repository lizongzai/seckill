package com.example.seckill.config.accessLimit;

import com.example.seckill.pojo.User;

public class UserContext {

  //将数据保存在当前线程保证，线程安全
  private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

  public static void setUserHolder(User user) {
    userHolder.set(user);
  }

  public static User getUserHolder() {
    return userHolder.get();
  }
}
