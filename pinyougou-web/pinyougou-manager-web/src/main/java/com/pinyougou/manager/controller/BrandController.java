package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Package: com.pinyougou.manager.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2018/12/26 0026  时间: 11:27
 * < >
 **/

@RestController
public class BrandController {
    // 注入service

    @Reference(timeout = 10000)
    private BrandService brandService;

    /*查询全部品牌*/
    @GetMapping("/brand/findAll")
    public List<Brand> findAll(){
        return brandService.findAll();
    }
}
