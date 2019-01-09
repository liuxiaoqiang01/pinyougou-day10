package com.pinyougou.shop.service;

import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.pinyougou.shop.service
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/2 0002  时间: 0:03
 * < >
 **/

/* 用户认证服务类  */

public class UserDetailsServiceImpl implements UserDetailsService {

    /* 注入商家服务接口代理对象*/
    private SellerService sellerService;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("sellerservice" + sellerService);

        // 创建List封装角色
        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        // 添加角色
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));

        // 根据登录名查询商家
        Seller seller = sellerService.findOne(username);

        // 判断商家是否为空, 并且已审核
        if (seller != null && seller.getStatus().equals("1")){


        // 返回用户信息对象
        return new User(username,seller.getPassword(),grantedAuths);

        }
        return null;

    }

    /* set 方法注入SellerService */
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }
}
