package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Package: com.pinyougou.shop.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/1 0001  时间: 18:27
 * < >
 **/

/* 商家控制器*/
@RestController
@RequestMapping("/seller")
public class SellerController {

    // 注入商家服务接口代理对象
    @Reference(timeout = 10000)
    private SellerService sellerService;


    /** 多条件查询商家*/
    @GetMapping("/findByPage")
    public PageResult findByPage(Seller seller,Integer page,Integer rows){
        try {
            // get请求中文转码
            if (seller != null && StringUtils.isNoneBlank(seller.getName())){
                seller.setName(new String(seller.getName()
                        .getBytes("ISO8859-1"),"UTF-8"));
            }

            if (seller != null && StringUtils.isNoneBlank(seller.getNickName())){
                seller.setNickName(new String(seller.getNickName()
                        .getBytes("ISO8859-1"),"UTF-8"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return sellerService.findByPage(seller,page,rows);
    }



    /** 审核商家(修改商家状态)*/
   @GetMapping("/updateStatus")
    public boolean updateStatus(String sellerId,String status){
       try {
           sellerService.updateStatus(sellerId,status);
           return true;
       }catch (Exception e){
           e.printStackTrace();
       }
            return false;
   }
}
