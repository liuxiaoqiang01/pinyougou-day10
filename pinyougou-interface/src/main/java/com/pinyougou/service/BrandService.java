package com.pinyougou.service;

import com.pinyougou.pojo.Brand;

import java.util.List;

/**
 * @Package: com.pinyougou.service
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2018/12/26 0026  时间: 11:22
 * < >
 **/
public interface BrandService {

    /*查询所有品牌*/
    List<Brand> findAll();
}
