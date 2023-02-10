package com.example.seckill.config.swagger.exception;

import com.example.seckill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局异常处理
 *
 * @author lizongzai
 * @date 2023/02/10 14:59
 * @since 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException{

  private RespBeanEnum respBeanEnum;

}
