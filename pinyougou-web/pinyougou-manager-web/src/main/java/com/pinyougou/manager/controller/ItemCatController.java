package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.BrandService;
import com.pinyougou.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Package: com.pinyougou.manager.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2018/12/26 0026  时间: 11:27
 * < >
 **/

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
    // 注入service

    @Reference(timeout = 10000)
    private ItemCatService itemCatService;



    /** 根据父级id 查询商品分类*/
    @GetMapping("/findItemCatByParentId")
    public List<ItemCat> findBrandList(Long parentId){
        return itemCatService.findItemCatByParentId(parentId);
    }
}
