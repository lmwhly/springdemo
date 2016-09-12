/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.luoo.mywork.modules.sys.web;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
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
    public Object newlist(HttpServletRequest request, @RequestBody JSONObject jsonObj) {

        Map<String, Object> map = new HashMap<String, Object>();
        String type = jsonObj.getString("type");
        String description = jsonObj.getString("description");
        map.put("type", type);
        map.put("description", description);


        int limit = jsonObj.getIntValue("limit");
        int offset = jsonObj.getIntValue("offset");

        try {

            PageBounds pageBounds = JSPUtil.getPagerBoundsByParameter(limit, offset);

            List<Log> list = logService.findNewTypeList(map, pageBounds);

            if (list != null && list.size() > 0) {
                Map<String, Object> retMap = (Map<String, Object>) JSPUtil.pagelistToJSONMapNew((PageList<Log>) list);
                return retMap;

            }

        } catch (Exception e) {
            logger.error("系统异常e:{}", e);
        }

        return null;
    }

}
