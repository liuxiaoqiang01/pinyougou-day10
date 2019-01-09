package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
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
 * @Date: Created in 2018/12/30 0030  时间: 9:33
 * < >
 **/
@Service(interfaceName = "com.pinyougou.service.TypeTemplateService")
@Transactional(readOnly = false, rollbackFor = RuntimeException.class)
public class TypeTemplateServiceImpl implements TypeTemplateService {

    // 注入TypeTemplateMapper
    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;


    @Override
    public void save(TypeTemplate typeTemplate) {
        typeTemplateMapper.insertSelective(typeTemplate);

    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public void deleteAll(Serializable[] ids) {
        // 创建示范对象
        Example example = new Example(TypeTemplate.class);

        // 创建条件对象
        Example.Criteria criteria = example.createCriteria();

        //添加in条件
        criteria.andIn("id", Arrays.asList(ids));

        typeTemplateMapper.deleteByExample(example);

    }


    /**
     *   根据主键id查询类型模板
     * @param id
     * @return
     */
    @Override
    public TypeTemplate findOne(Serializable id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<TypeTemplate> findAll() {
        return null;
    }

    @Override
    public PageResult findByPage(TypeTemplate typeTemplate, int page, int rows) {

        try {
            // 开始分页
            PageInfo<TypeTemplate> pageInfo = PageHelper.startPage(page, rows)
                    .doSelectPageInfo(new ISelect() {
                        @Override
                        public void doSelect() {
                            typeTemplateMapper.findAll(typeTemplate);
                        }
                    });
            return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
}


    /** 根据模版 id 查询所有的规格与规格选项 */
    @Override
    public List<Map> findSpecByTemplateId(Long id) {

        try {
            //根据主键id查询模板
            TypeTemplate typeTemplate = findOne(id);


            /**
             *  [{"id":33,"text":"电视屏幕尺寸"}]
             *  获取所有的规格选项, 转换成List集合
             */
            List<Map> specLists = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);

            // 遍历集合
            for (Map map : specLists) {
                // 创建规格选项对象, 用于封装数据
                SpecificationOption so = new SpecificationOption();
                so.setSpecId(Long.valueOf(map.get("id").toString()));

                // 通过规格id ,  查询规格选项数据
                List<SpecificationOption> specOptions = specificationOptionMapper.select(so);
                map.put("options",specOptions);
            }

            return specLists;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
