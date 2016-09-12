/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.luoo.mywork.modules.sys.web;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.luoo.mywork.common.mapper.JsonMapper;
import com.luoo.mywork.common.web.BaseController;
import com.luoo.mywork.modules.sys.entity.Log;
import com.luoo.mywork.modules.sys.service.LogService;
import com.luoo.mywork.modules.sys.utils.JSPUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志Controller
 *
 * @author ThinkGem
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @RequiresPermissions("sys:log:view")
    @RequestMapping(value = {"list", ""})
    public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
       /* Page<Log> page = logService.findPage(new Page<Log>(request, response), log);
        model.addAttribute("page", page);*/
        return "modules/sys/logList";
    }


    @RequiresPermissions("sys:log:view")
    @RequestMapping(value = "newlist")
    @ResponseBody
    public String newlist(HttpServletRequest request, @RequestBody JSONObject jsonObj) {

        Map<String, Object> map = new HashMap<String, Object>();
        String title = jsonObj.getString("title");
        String id = jsonObj.getString("id");
        String requestUri = jsonObj.getString("requestUri");
        String beginDate = jsonObj.getString("beginDate");
        String endDate = jsonObj.getString("endDate");
        String exception = jsonObj.getString("exception");

        map.put("title", title);
        map.put("id", id);
        map.put("requestUri", requestUri);
        map.put("beginDate", beginDate);
        map.put("endDate", endDate);
        map.put("exception", exception);
        map.put("id", id);

        int limit = jsonObj.getIntValue("limit");
        int offset = jsonObj.getIntValue("offset");

        try {

            PageBounds pageBounds = JSPUtil.getPagerBoundsByParameter(limit, offset);

            List<Log> list = logService.findNewTypeList(map, pageBounds);

            if (list != null && list.size() > 0) {
                Map<String, Object> retMap = (Map<String, Object>) JSPUtil.pagelistToJSONMapNew((PageList<Log>) list);
                String ss  = JsonMapper.toJsonString(retMap);
                return ss;

            }

        } catch (Exception e) {
            logger.error("系统异常e:{}", e);
        }

        return null;
    }

}
