package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Goods;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * GoodsMapper 数据访问接口
 * @date 2018-12-27 20:34:36
 * @version 1.0
 */
public interface GoodsMapper extends Mapper<Goods>{


    /**多条件查询商家的商品 */
    /* 查询需要的列: 用 List<Map<String, Object>> 集合封装数据 */
    List<Map<String,Object>> findAll(Goods goods);


    /* 批量修改商品审核状态*/
    void updateStatus(@Param("cloumnName") String cloumnName,
                      @Param("ids") Long[] ids,
                      @Param("status") String status);


    /* 商品删除，修改商品删除状态 */
    void updateDeleteStatus(@Param("ids") Serializable[] ids, @Param("isDelete") String isDelete);

    /** 修改商品上下架的状态*/
   void updateMarketable(@Param("ids") Long[] ids, @Param("status") String status);
}