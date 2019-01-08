package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.service.ItemSearchService;
import com.pinyougou.solr.SolrItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Package: com.pinyougou.search.service.impl
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/8 0008  时间: 21:15
 * < >
 **/


@Service(interfaceName = "com.pinyougou.service.ItemSearchService")
// 此时不需要 @Transactional 这个注解, 因为这是从索引库查询数据,而不是数据库
public class ItemSearchServiceImpl implements ItemSearchService {
    // 注入 SolrTemplate
    @Autowired
    private SolrTemplate solrTemplate;


    /* 搜索商品的方法*/
    @Override
    public Map<String, Object> search(Map<String, Object> params) {
        try {
            // 定义Map 集合, 用于封装返回数据
            Map<String,Object> data = new HashMap<>();

            // 创建查询对象
            Query query = new SimpleQuery("*:*");

            // 获取检索关键字
            String keywords = (String) params.get("keywords");


            if (StringUtils.isNoneBlank(keywords)){
                // 创建查询条件
                Criteria criteria = new Criteria("keywords").is(keywords);

                // 添加查询条件
                query.addCriteria(criteria);

                // 说明: 还可以用query 对象设置分页开始记录数(第一页), 默认为0,
                //      和每页显示的记录数(页大小), 默认为10
            }

            // 分页搜索商品数据, 得到分数分页对象
            ScoredPage<SolrItem> scoredPage =
                    solrTemplate.queryForPage(query, SolrItem.class);
            System.out.println("总记录数: " + scoredPage.getTotalElements());



            // 获取分页数据结果
            List<SolrItem> solrItems = scoredPage.getContent();
            for (SolrItem solrItem : solrItems) {
                System.out.println("品牌: " + solrItem.getBrand() + " ===> 商品分类: " + solrItem.getCategory()
                        + " ===> 商品标题: " + solrItem.getTitle() + " ===> 商家: " + solrItem.getSeller());
            }



            data.put("rows", solrItems);
            return data;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
