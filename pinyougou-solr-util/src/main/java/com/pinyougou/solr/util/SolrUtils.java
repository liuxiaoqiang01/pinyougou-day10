package com.pinyougou.solr.util;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Item;
import com.pinyougou.solr.SolrItem;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Package: com.pinyougou.solr.util
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/8 0008  时间: 15:14
 * < >
 **/

/*  查询SKU商品类 */
@Component
public class SolrUtils {
    // 注入ItemMapper
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrTemplate solrTemplate;

    /* 导入商品数据 (正常审核的商品)*/
    public void importItemData(){
        // 创建商品类, 用途封装数据
        Item item = new Item();
        // 设置商品的审核状态(审核正常的商品)
        item.setStatus("1");

        // 从数据库表中查询商品数据
        List<Item> itemList = itemMapper.select(item);
        System.out.println("=========商品列表===========");

        List<SolrItem> solrItemList = new ArrayList<>();
        for (Item item1 : itemList) {
            SolrItem solrItem = new SolrItem();

            solrItem.setId(item1.getId());
            solrItem.setBrand(item1.getBrand());
            solrItem.setCategory(item1.getCategory());
            solrItem.setGoodsId(item1.getGoodsId());
            solrItem.setImage(item1.getImage());
            solrItem.setPrice(item1.getPrice());
            solrItem.setSeller(item1.getSeller());
            solrItem.setTitle(item1.getTitle());
            solrItem.setUpdateTime(item1.getUpdateTime());

            // 将spec 字段的json字符串转换成Map
            Map maps = JSON.parseObject(item1.getSpec(), Map.class);
            // 设置域
            solrItem.setSpecMap(maps);

            solrItemList.add(solrItem);
        }

        UpdateResponse updateResponse = solrTemplate.saveBeans(solrItemList);
        if (updateResponse.getStatus() == 0){
            solrTemplate.commit();
        }else {
            solrTemplate.rollback();
        }

        System.out.println("==============查询结束===============");
    }




    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");


        SolrUtils solrUtils = context.getBean(SolrUtils.class);
        solrUtils.importItemData();

    }
}
