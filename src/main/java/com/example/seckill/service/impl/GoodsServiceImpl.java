package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.mapper.GoodsMapper;
import com.example.seckill.pojo.Goods;
import com.example.seckill.vo.GoodsVO;
import com.example.seckill.service.IGoodsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizongzai
 * @since 2023-02-12
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

  @Autowired
  private GoodsMapper goodsMapper;

  /**
   * 功能描述: 获取所有秒杀商品详情
   *
   * @return
   */
  @Override
  public List<GoodsVO> findGoodsVo() {
    System.out.println("获取所有商品列表 = " +  goodsMapper.findGoodsVo());
    return goodsMapper.findGoodsVo();
  }

  /**
   * 获取商品详情
   *
   * @param goodsId
   * @return
   */
  @Override
  public GoodsVO findGoodsByGoodsId(Long goodsId) {

    System.out.println("秒杀商品详情aaaaaaaaa = " + goodsMapper.findGoodsByGoodsId(goodsId));
    return goodsMapper.findGoodsByGoodsId(goodsId);
  }
}
