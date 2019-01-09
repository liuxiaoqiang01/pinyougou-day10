package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Package: com.pinyougou.sellergoods.service.impl
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2018/12/26 0026  时间: 11:24
 * < >
 **/
@Service(interfaceName = "com.pinyougou.service.BrandService") // dubbo的Service注解:
@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
public class BrandServiceImpl implements BrandService {

    /*注入访问接口代理对象*/
    @Autowired
    private BrandMapper brandMapper;


    @Override
    public void save(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public void deleteAll(Serializable[] ids) {
        // 创建示范对象
        Example example = new Example(Brand.class);
        // 创建条件对象
        Example.Criteria criteria = example.createCriteria();
        // 添加条件
        criteria.andIn("id", Arrays.asList(ids));
        // 根据条件删除
        brandMapper.deleteByExample(example);

    }

    @Override
    public Brand findOne(Serializable id) {
        return null;
    }


    /*查询所有品牌*/
    @Override
    public List<Brand> findAll() {
               return brandMapper.selectAll();
    }

    @Override
    public PageResult findByPage(Brand brand, int page, int rows) {
        try {

        // 开始分页
        PageInfo<Object> pageInfo = PageHelper.startPage(page, rows)
                .doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                brandMapper.findAll(brand);
            }
        });
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> findAllByIdAndName() {
        try {
            return brandMapper.findAllByIdAndName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
