package com.example.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seckill.pojo.Goods;
import com.example.seckill.vo.GoodsVO;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lizongzai
 * @since 2023-02-12
 */
public interface GoodsMapper extends BaseMapper<Goods> {

  /**
   * 功能描述: 获取所有秒杀商品详情
   *
   * @return
   */
  List<GoodsVO> findGoodsVo();

}
