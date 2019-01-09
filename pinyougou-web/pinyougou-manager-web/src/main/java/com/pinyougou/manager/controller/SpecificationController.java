package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationService;
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
@RequestMapping("/specification")
public class SpecificationController {
    // 注入service

    @Reference(timeout = 10000)
    private SpecificationService specificationService;




    /** 根据规格主键查询规格选项*/
    @GetMapping("/findSpecOption")
    public List<SpecificationOption> findSpecOption(Long id){

    return specificationService.findSpecOption(id);

}
    /*分页查询规格*/
    @GetMapping("/findByPage")
    public PageResult findByPage(Specification specification,Integer page, Integer rows){

        /*get 请求中文转码*/
        if (specification != null && StringUtils.isNoneBlank(specification.getSpecName())){
            try {
                specification.setSpecName(new String(specification.getSpecName().getBytes("ISO8859-1"),"utf-8"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return specificationService.findByPage(specification,page,rows);

    }

    /*删除规格数据*/
    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try {
            specificationService.deleteAll(ids);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }




    /*添加规格数据*/
    @PostMapping("/save")
    public boolean save(@RequestBody Specification specification){
        try {
            specificationService.save(specification);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /*修改规格数据*/
    @PostMapping("/update")
    public boolean update(@RequestBody Specification specification){
        try {
            specificationService.update(specification);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /** 根据id和name查询所有的品牌*/
    @GetMapping("/findSpecList")
    public List<Map<String,Object>> findSpecList(){
        return specificationService.findAllByIdAndName();
    }
}
