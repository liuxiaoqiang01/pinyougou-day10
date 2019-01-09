package com.pinyougou.manager.controller;

/**
 * @Package: com.pinyougou.manager.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/1 0001  时间: 12:39
 * < >
 **/

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/** 获取登录用户名控制器*/
@RestController
public class LoginController {
    /** 显示登录用户名*/
    @GetMapping("/showLoginName")
    public Map<String,String> showLoginName(){
        // 获取登录用户名
        String loginName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // 定义Map集合封装数据, 返回给前端页面
        Map<String,String> data = new HashMap<>();
        data.put("loginName",loginName);

        return data;
    }

}
