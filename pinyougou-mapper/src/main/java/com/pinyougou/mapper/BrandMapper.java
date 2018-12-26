package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Package: com.pinyougou.mapper
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2018/12/26 0026  时间: 11:18
 * < >
 **/
public interface BrandMapper {

    /*查询全部品牌*/
    @Select("select * from tb_brand")
    List<Brand> findAll();
}
