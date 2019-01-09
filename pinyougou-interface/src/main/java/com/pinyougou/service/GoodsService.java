package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;
import java.util.List;
import java.io.Serializable;
/**
 * GoodsService 服务接口
 * @date 2018-12-27 20:35:20
 * @version 1.0
 */
public interface GoodsService {

	/** 添加方法 */
	void save(Goods goods);

	/** 修改方法 */
	void update(Goods goods);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 商品删除，修改商品删除状态 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	Goods findOne(Serializable id);

	/** 查询全部 */
	List<Goods> findAll();

	/** 多条件分页查询 */
	PageResult findByPage(Goods goods, int page, int rows);

	/** 批量修改商品审核状态*/
	void updateStatus(String cloumnName,Long[] ids, String status);

	/** 修改商品上下架的状态*/
   void updateMarketable(Long[] ids, String status);

}