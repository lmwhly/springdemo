package com.luoo.mywork.modules.sys.utils;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luoo
 * @create 2016-09-08 18:33
 */

public class JSPUtil {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object pagelistToJSONMapNew(PageList list) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (list != null) {
            Paginator paginator = list.getPaginator();
            map.put("total", paginator.getTotalCount());
            map.put("rows", new ArrayList(list));
        }
        return map;
    }

    /**
     * 取得分页对象
     *
     * @param pageSize
     * @param offset
     * @return
     */
    @SuppressWarnings("unused")
    public static PageBounds getPagerBoundsByParameter(int pageSize, int offset) {
        if (pageSize == 0) {
            return null;
        }

        PageBounds pageBounds = new PageBounds(offset / pageSize + 1, pageSize);
        return pageBounds;
    }
}
