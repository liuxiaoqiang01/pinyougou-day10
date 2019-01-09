package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.BrandService;
import com.pinyougou.service.TypeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Package: com.pinyougou.manager.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2018/12/26 0026  时间: 11:27
 * < >
 **/

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    // 注入service

    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;

    /*分页查询类型模板*/
    @GetMapping("/findByPage")
    public PageResult findByPage(TypeTemplate typeTemplate, Integer page, Integer rows){

        if (typeTemplate != null && StringUtils.isNoneBlank(typeTemplate.getName())){
            try {
                typeTemplate.setName(new String(typeTemplate.getName().getBytes("ISO8859-1"),"utf-8"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return typeTemplateService.findByPage(typeTemplate,page,rows);

    }

    /*删除类型模板数据*/
    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try {
            typeTemplateService.deleteAll(ids);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }




    /*添加类型模板数据*/
    @PostMapping("/save")
    public boolean save(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.save(typeTemplate);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /*修改类型模板数据*/
    @PostMapping("/update")
    public boolean update(@RequestBody TypeTemplate typeTemplate){
        try {
            typeTemplateService.update(typeTemplate);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
