package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Package: com.pinyougou.sellergoods.service.impl
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2018/12/26 0026  时间: 11:24
 * < >
 **/
@Service(interfaceName = "com.pinyougou.service.BrandService") // dubbo的Service注解:
@Transactional
public class BrandServiceImpl implements BrandService {

    /*注入访问接口代理对象*/
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
