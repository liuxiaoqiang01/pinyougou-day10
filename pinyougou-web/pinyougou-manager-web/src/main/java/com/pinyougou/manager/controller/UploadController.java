package com.pinyougou.manager.controller;

import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.pinyougou.shop.controller
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/2 0002  时间: 23:18
 * < >
 **/

@RestController
public class UploadController {

    // 注入文件服务器访问地址
    @Value("${fileServerUrl}")
    private String fileServerUrl;

    /* 文件上传*/
    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file")MultipartFile multipartFile){

        Map<String,Object> data = new HashMap<>();
        data.put("status",500);

        try {
            String cong_fileName = this.getClass().getResource("/fastdfs_client.conf").getPath();

            /** 初始化客户端全局对象*/
            ClientGlobal.init(cong_fileName);

            // 创建客户端存储对象
            StorageClient storageClient = new StorageClient();

            // 获取源文件名
            String originalFilename = multipartFile.getOriginalFilename();
            // 上传文件到FastDFS服务器
            String[] arr = storageClient
                    .upload_file(multipartFile.getBytes(),
                            FilenameUtils.getExtension(originalFilename),
                            null);

            // 拼接返回的url 和ip地址 , 拼装成完整的url
            StringBuilder url = new StringBuilder(fileServerUrl);
            for (String str : arr) {
                url.append("/" + str);

            }

            data.put("status",200);
            data.put("url",url.toString());

        }catch (Exception e){
            e.printStackTrace();
        }

        return data;
    }
}
