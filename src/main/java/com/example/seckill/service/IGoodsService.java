package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.pojo.Goods;
import com.example.seckill.vo.GoodsVO;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lizongzai
 * @since 2023-02-12
 */
public interface IGoodsService extends IService<Goods> {

  /**
   * 功能描述: 获取所有秒杀商品详情
   *
   * @return
   */
  List<GoodsVO> findGoodsVo();

  /**
   * 获取商品详情
   *
   * @param goodsId
   * @return
   */
  GoodsVO findGoodsByGoodsId(Integer goodsId);
}
