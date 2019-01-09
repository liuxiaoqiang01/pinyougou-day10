package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Package: com.pinyougou.shop.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/3 0003  时间: 10:35
 * < >
 **/

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    /* 注入服务层接口代理对象*/
    @Reference(timeout = 10000)
    TypeTemplateService typeTemplateService;


    /* 根据主键id查询类型模板*/
    @GetMapping("/findOne")
    public TypeTemplate findOne(Long id){
        return  typeTemplateService.findOne(id);
    }


    /* 根据类型模板ID 查询所有的 规格与 规格选项*/
    @GetMapping("/findSpecByTemplateId")
    public List<Map> findSpecByTemplateId(Long id){
        return typeTemplateService.findSpecByTemplateId(id);
    }
}
