package com.pinyougou.sellergoods.service.impl;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Package: com.pinyougou.sellergoods.service.impl
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/2 0002  时间: 20:58
 * < >
 **/
@Service(interfaceName = "com.pinyougou.service.GoodsService")
@Transactional
public class GoodsServiceImpl implements GoodsService {
    // 引入数据访问层代理对象
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SellerMapper sellerMapper;


    /* 添加商品 */
    @Override
    public void save(Goods goods) {
        try {
            // 设置未审核状态
            goods.setAuditStatus("0");
            // 添加SKU商品表
            goodsMapper.insertSelective(goods);

            // 添加商品描述表
            goods.getGoodsDesc().setGoodsId(goods.getId());
            goodsDescMapper.insertSelective(goods.getGoodsDesc());

            // 判断用户是否启用了规格
            if ("1".equals(goods.getIsEnableSpec())) { // 启用规格
                /** 迭代所有的SKU具体商品集合，往SKU表插入数据 */
                for (Item item : goods.getItems()) {
                    // item:  [{"spec":{"网络":"移动4G","机身内存":"32G"},
                    // "price":"2000","num":9999,"status":1,"isDefault":"0"}]


                    // 定义ＳＫＵ商品的标题
                    // 商品标题(SPU的商品名称 +  规格选项)
                    StringBuilder title = new StringBuilder(goods.getGoodsName());
                    //title.append(goods.getGoodsName());

                    // 把规格选项字符串 转化成Map集合
                    Map<String, String> spec = JSON.parseObject(item.getSpec(), Map.class);
                    for (String value : spec.values()) {
                        title.append(" " + value);

                    }

                    item.setTitle(title.toString());


                    // 设置商品的其他属性
                    setItemInfo(goods,item);


                  // 往tb_item 表插入数据
                    itemMapper.insertSelective(item);
                }
            }else {   // 没有启用规格
                // 往tb_item 表中插入一条数据, 此时SPU=SKU
                Item item = new Item();

                // 设置商品的标题
                item.setTitle(goods.getGoodsName());
                // 规格
                item.setSpec("{}");
                // 价格
                item.setPrice(goods.getPrice());
                // 库存数量
                item.setNum(100);
                // 状态吗
                item.setStatus("1");
                // 是否默认
                item.setIsDefault("1 ");

                // 设置商品的其他属性
                setItemInfo(goods,item);


                // 往tb_item 表插入数据
                itemMapper.insertSelective(item);
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /* 设置商品的其他信息 */
    private void setItemInfo(Goods goods, Item item){

        // 获取该商品的所有图片
        List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
        if (imageList != null && imageList.size() > 0) {
            // 商品图片
            item.setImage(imageList.get(0).get("url").toString());
        }

        // 商品三级分类的id
        item.setCategoryid(goods.getCategory3Id());
        // 创建时间
        item.setCreateTime(new Date());
        // 修改时间
        item.setUpdateTime(item.getCreateTime());
        // 关联的SPU的id
        item.setGoodsId(goods.getId());
        // 商家id
        item.setSellerId(goods.getSellerId());


        // 商品三级分类(三级分类)
        // 查询三级商品分类对象
        ItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id());
        item.setCategory(itemCat != null ? itemCat.getName() : "");


        // 品牌名称
        // 查询品牌对象(根据品牌id查)
        Brand brand = brandMapper.selectByPrimaryKey(goods.getBrandId());
        item.setBrand(brand != null ? brand.getName() : "");


        // 店铺名称
        // 查询商家对象
        Seller seller = sellerMapper.selectByPrimaryKey(goods.getSellerId());
        item.setSeller(seller != null ? seller.getNickName() : "");
    }


    @Override
    public void update(Goods goods) {

    }

    @Override
    public void delete(Serializable id) {

    }


    /** 商品删除，修改商品删除状态 */
    @Override
    public void deleteAll(Serializable[] ids) {

        try {
            goodsMapper.updateDeleteStatus(ids,"1");

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Goods findOne(Serializable id) {
        return null;
    }

    @Override
    public List<Goods> findAll() {
        return null;
    }

    @Override
    public PageResult findByPage(Goods goods, int page, int rows) {

        try {
        // 开始分页
            PageInfo<Map<String,Object>> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    goodsMapper.findAll(goods);
                }
            });


             //循环查询到的商品
            for (Map<String,Object> map : pageInfo.getList()) {

              /**
                 {{entity.category1Name}}
                 {{entity.category2Name}}
                 {{entity.category3Name}}
                 {{entity.auditStatus}}
              */

                ItemCat itemCat1 = itemCatMapper.selectByPrimaryKey(map.get("category1Id"));
                map.put("category1Name", itemCat1 != null ? itemCat1.getName(): "");

                ItemCat itemCat2 = itemCatMapper.selectByPrimaryKey(map.get("category2Id"));
                map.put("category2Name", itemCat2 != null ? itemCat2.getName(): "");

                ItemCat itemCat3 = itemCatMapper.selectByPrimaryKey(map.get("category3Id"));
                map.put("category3Name", itemCat3 != null ? itemCat3.getName(): "");

            }

            return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        }catch (Exception e){
            throw new RuntimeException(e);

        }
    }

    /* 批量修改商品审核状态*/
    @Override
    public void updateStatus(String cloumnName,Long[] ids, String status) {
        try {

            goodsMapper.updateStatus(cloumnName,ids,status);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

   /** 修改商品上下架的状态*/
    @Override
    public void updateMarketable(Long[] ids, String status){
        try {

            goodsMapper.updateMarketable(ids,status);
        }catch (Exception e){
         throw new RuntimeException(e);
        }
    }
}
