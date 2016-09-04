package com.luoo.mywork.modules.oa.web;

/**
 * @author Luoo2
 * @create 2016-09-03 18:28
 */

import com.luoo.mywork.modules.oa.entity.OaNotify;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通知通告Controller
 * @author ThinkGem
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNotify")
public class OaNotifyController {


    /**
     * 获取我的通知数目
     */
    @RequestMapping(value = "self/count")
    @ResponseBody
    public String selfCount(OaNotify oaNotify, Model model) {
        oaNotify.setSelf(true);
        oaNotify.setReadFlag("0");
//        return String.valueOf(oaNotifyService.findCount(oaNotify));
        return "0";
    }
}
