/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.luoo.mywork.modules.sys.web;

import com.alibaba.fastjson.JSONObject;
import com.luoo.mywork.common.mapper.JsonMapper;
import com.luoo.mywork.common.persistence.Page;
import com.luoo.mywork.common.utils.DateUtils;
import com.luoo.mywork.common.web.BaseController;
import com.luoo.mywork.modules.sys.entity.Log;
import com.luoo.mywork.modules.sys.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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


        // 设置默认时间范围，默认当前月
        if (log.getBeginDate() == null){
            log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
        }
        if (log.getEndDate() == null){
            log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
        }
        model.addAttribute("log", log);

        return "modules/sys/logList";
    }


    @RequiresPermissions("sys:log:view")
    @RequestMapping(value = "newlist")
    @ResponseBody
    public Object newlist(Log log, HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObj) {

        String title = jsonObj.getString("title");
        String id = jsonObj.getString("id");
        String requestUri = jsonObj.getString("requestUri");
        Date beginDate = jsonObj.getDate("beginDate");
        Date endDate = jsonObj.getDate("endDate");
        String exception = jsonObj.getString("exception");

        log.setId(id);
        log.setRequestUri(requestUri);
        log.setTitle(title);
        log.setBeginDate(beginDate);
        log.setEndDate(endDate);
        log.setException(exception);

        int pageNumber = jsonObj.getIntValue("pageNumber");
        int pageSize = jsonObj.getIntValue("pageSize");

        try {

            Page<Log> page = logService.findPage(new Page<Log>(pageNumber, pageSize), log);


            return JsonMapper.toJsonString(page);


        } catch (Exception e) {
            logger.error("系统异常e:{}", e);
        }

        return null;
    }

}
