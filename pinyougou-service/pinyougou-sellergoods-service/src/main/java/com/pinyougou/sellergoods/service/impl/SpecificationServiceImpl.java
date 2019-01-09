package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Package: com.pinyougou.sellergoods.service.impl
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2018/12/29 0029  时间: 14:36
 * < >
 **/
@Service(interfaceName = "com.pinyougou.service.SpecificationService")
@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
public class SpecificationServiceImpl implements SpecificationService{
    // 注入specificationMapper
    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public void save(Specification specification) {

        try {
            // 往规格表插入数据
            specificationMapper.insertSelective(specification);
            // 往规格选项表插入数据
            specificationOptionMapper.save(specification);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** 修改规格与规格选项*/
    @Override
    public void update(Specification specification) {


        try {
            // 修改规格表数据
            specificationMapper.updateByPrimaryKeySelective(specification);

            /**########### 修改规格选项表数据 ###########*/
            // 第一步：删除规格选项表中的数据 spec_id
            // delete from tb_specification_option where spec_id = ?
            // 创建规格选项对象，封装删除条件 通用Mapper

            SpecificationOption so = new SpecificationOption();
            so.setSpecId(specification.getId());
            specificationOptionMapper.delete(so);

            // 往规格选项表插入数据
            specificationOptionMapper.save(specification);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Serializable id) {

    }

    // 批量删除规格与规格选项
    @Override
    public void deleteAll(Serializable[] ids) {
        try {
            for (Serializable id : ids) {
                SpecificationOption so = new SpecificationOption();
                so.setSpecId((Long) id);
                specificationOptionMapper.delete(so);
                specificationMapper.deleteByPrimaryKey(id);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Specification findOne(Serializable id) {
        return null;
    }

    @Override
    public List<Specification> findAll() {
        return null;
    }


    /*多条件分页查询规格*/
    @Override
    public PageResult findByPage(Specification specification, int page, int rows) {
        try {
            // 开始分页
            PageInfo<Specification> pageInfo = PageHelper.startPage(page, rows)
                    .doSelectPageInfo(new ISelect() {
                        @Override
                        public void doSelect() {

                            specificationMapper.findAll(specification);
                        }
                    });
            return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }


    /** 根据规格主键查询规格选项*/
    @Override
    public List<SpecificationOption> findSpecOption(Long id) {
        try {
            // 创建规格选项对象封装查询条件
            SpecificationOption so = new SpecificationOption();
            so.setSpecId(id);
            return specificationOptionMapper.select(so);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /** 根据id和specName查询所有的规格*/
    @Override
    public List<Map<String, Object>> findAllByIdAndName() {
        try {
            return specificationMapper.findAllByIdAndName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
