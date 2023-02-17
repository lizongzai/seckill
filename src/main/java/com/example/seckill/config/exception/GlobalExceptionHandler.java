package com.example.seckill.config.exception;

import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 * @author lizongzai
 * @date 2023/02/10 14:59
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public RespBean exceptionHandler(Exception exception) {

    // 全局异常
    if (exception instanceof GlobalException) {
      GlobalException ex = (GlobalException) exception;
      return RespBean.error(ex.getRespBeanEnum());
    }

    //参数校验异常
    else if (exception instanceof BindException) {
      BindException ex = (BindException) exception;
      RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
      //获取所有绑定结果的错误,并且只获取第一个默认错误
      respBean.setMessage("参数校验异常: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
      return respBean;
    }

    //服务端异常
    return RespBean.error(RespBeanEnum.ERROR);
  }
}
