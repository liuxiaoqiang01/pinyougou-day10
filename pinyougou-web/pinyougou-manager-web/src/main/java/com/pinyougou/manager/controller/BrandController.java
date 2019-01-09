package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
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
@RequestMapping("/brand")
public class BrandController {
    // 注入service

    @Reference(timeout = 10000)
    private BrandService brandService;

    /*分页查询品牌*/
    @GetMapping("/findByPage")
    public PageResult findByPage(Brand brand,Integer page, Integer rows){

        if (brand != null && StringUtils.isNoneBlank(brand.getName())){
            try {
                brand.setName(new String(brand.getName().getBytes("ISO8859-1"),"utf-8"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return brandService.findByPage(brand,page,rows);

    }

    /*删除品牌数据*/
    @PostMapping("/delete")
    public boolean delete(Long[] ids){
        try {
            brandService.deleteAll(ids);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }




    /*添加品牌数据*/
    @PostMapping("/save")
    public boolean save(@RequestBody Brand brand){
        try {
            brandService.save(brand);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /*修改品牌数据*/
    @PostMapping("/update")
    public boolean update(@RequestBody Brand brand){
        try {
            brandService.update(brand);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /** 根据id和name查询所有的品牌*/
    @GetMapping("/findBrandList")
    public List<Map<String,Object>> findBrandList(){
        return brandService.findAllByIdAndName();
    }
}
