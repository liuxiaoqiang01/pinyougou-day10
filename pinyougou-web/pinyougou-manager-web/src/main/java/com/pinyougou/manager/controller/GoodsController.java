package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;
import com.pinyougou.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @Package: com.pinyougou.shop.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/2 0002  时间: 20:40
 * < >
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {
    // 注入服务层接口
    @Reference(timeout = 100000)
    private GoodsService goodsService;


    /*  添加商品  */
    @PostMapping("/save")
    public boolean save(@RequestBody Goods goods){

        try {
            // 获取登录用户名
            String sellerId = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

            goods.setSellerId(sellerId);
            goodsService.save(goods);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /* 分页查询 商家的商品*/
    @GetMapping("/findByPage")
    public PageResult findByPage(Goods goods, Integer page, Integer rows){
        try {

            // 添加查询条件
            goods.setAuditStatus("0");

            // get 请求中文乱码
            if (StringUtils.isNoneBlank(goods.getGoodsName())){
            goods.setGoodsName(new String(goods.getGoodsName()
                    .getBytes("ISO8859-1"),"UTF-8"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return goodsService.findByPage(goods,page,rows);
    }

    /* 批量修改商品审核状态*/
    @GetMapping("/updateStatus")
    public boolean updateStatus( Long[] ids, String status){
        try {
            goodsService.updateStatus("audit_status",ids,status);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /** 商品删除，修改商品删除状态 */
    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try {
            goodsService.updateStatus("is_delete",ids,"1");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
