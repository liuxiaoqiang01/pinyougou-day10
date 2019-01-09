package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.Specification;

import java.util.List;
import java.util.Map;

/**
 * SpecificationMapper 数据访问接口
 * @date 2018-12-27 20:34:36
 * @version 1.0
 */
public interface SpecificationMapper extends Mapper<Specification>{

    /*多条件查询规格*/
    List<Specification> findAll(Specification specification);

    @Select("select id,spec_name as text from tb_specification  order by id asc")
    List<Map<String,Object>> findAllByIdAndName();
}