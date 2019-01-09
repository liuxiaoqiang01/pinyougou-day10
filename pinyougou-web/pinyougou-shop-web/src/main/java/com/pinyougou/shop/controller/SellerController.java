package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.pinyougou.shop.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/1 0001  时间: 18:27
 * < >
 **/
@RestController
@RequestMapping("/seller")
public class SellerController {

    // 注入商家服务接口代理对象
    @Reference(timeout = 10000)
    private SellerService sellerService;

    /** 添加商家*/
    @PostMapping("/save")
    public boolean save(@RequestBody Seller seller){
        try {
            /* 密码加密*/
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode(seller.getPassword());
            seller.setPassword(password);

            // 调用服务层
            sellerService.save(seller);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
