package com.pinyougou.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.Content;
import com.pinyougou.service.ContentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Package: com.pinyougou.portal.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/6 0006  时间: 21:06
 * < >
 **/
@RestController
public class ContentController {

    @Reference(timeout = 10000)
    private ContentService contentService;


    /* 根据广告分类Id 查询广告内容*/
    @GetMapping("/findContentByCategoryId")
    public List<Content> findContentByCategoryId(Long categoryId){

        return contentService.findContentByCategoryId(categoryId);
    }
}
