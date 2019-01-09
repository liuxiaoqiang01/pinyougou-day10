package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SellerMapper;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package: com.pinyougou.sellergoods.service.impl
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/1 0001  时间: 18:34
 * < >
 **/
@Service(interfaceName = "com.pinyougou.service.SellerService")
@Transactional
public class SellerServiceImpl implements SellerService{
    @Autowired
    private SellerMapper sellerMapper;

    /** 添加商家*/
    @Override
    public void save(Seller seller) {
        try {
            seller.setStatus("0");
            seller.setCreateTime(new Date());
            sellerMapper.insertSelective(seller);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public void deleteAll(Serializable[] ids) {

    }

    @Override

    public Seller findOne(Serializable id) {
        return sellerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }


    /** 多条件分页查询商家*/
    @Override
    public PageResult findByPage(Seller seller, int page, int rows) {
        try {
            // 开始分页
            PageInfo<Object> pageInfo = PageHelper.startPage(page, rows).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    sellerMapper.findAll(seller);
                }
            });

            return new PageResult(pageInfo.getTotal(),pageInfo.getList());

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateStatus(String sellerId, String status) {
        try {
            // 创建商家类(Seller) 用于封装数据(封装商家id, 审核状态)
            // update tb_seller status=? where seller_id = ? ;
            Seller seller = new Seller();
            seller.setSellerId(sellerId);
            seller.setStatus(status);

            // 调用 根据主键选择性修改 的方法
            sellerMapper.updateByPrimaryKeySelective(seller);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
