package com.pinyougou.service;

import java.util.Map; /**
 * @Package: com.pinyougou.service
 * @ClassName: CLASS_NAME
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author: LiuXiaoQiang
 * @Date: Created in 2019/1/8 0008  时间: 21:13
 * < >
 **/
public interface ItemSearchService {
    /* 搜索方法*/
    Map<String,Object> search(Map<String, Object> params);
}
