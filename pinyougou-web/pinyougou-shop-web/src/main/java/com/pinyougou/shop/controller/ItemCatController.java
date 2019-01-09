package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Package: com.pinyougou.shop.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/3 0003  时间: 9:08
 * < >
 **/
/* 商品分类下拉选择框控制器*/

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    // 注入(商品分类) 服务层接口代理对象
    @Reference(timeout = 10000)
    private ItemCatService itemCatService;

    @GetMapping("/findItemCatByParentId")
    public List<ItemCat> findItemCatByParentId(@RequestParam(value = "parentId",
            defaultValue = "0") Long parentId){

        return itemCatService.findItemCatByParentId(parentId);
    }
}
