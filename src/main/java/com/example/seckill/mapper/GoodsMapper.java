package com.example.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.seckill.pojo.Goods;
import com.example.seckill.vo.GoodsVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
  List<GoodsVo> findGoodsVo();

  /**
   * 获取商品详情
   *
   * @param goodsId
   * @return
   */
  GoodsVo findGoodsByGoodsId(@Param("goodsId") Long goodsId);
}
