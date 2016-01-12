package com.controller;

import com.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Luoo on 2016/1/11.
 */
@Controller
public class TestController {

    @RequestMapping("/home")
    public ModelAndView showTest() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "OK!!!");
        mv.setViewName("nexthome");
        return mv;
    }

    @RequestMapping("/home1")
    public ModelAndView requestparam1(@RequestParam(value = "username", required = true, defaultValue = "luoo") String username) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", username);
        mv.setViewName("nexthome");
        return mv;
    }

    //users/456/topics/123
    @RequestMapping(value = "/users/{userid}/topics/{topicid}")
    public ModelAndView testPathVariable(@PathVariable(value = "userid") int userid, @PathVariable(value = "topicid") int topicid) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", userid + " and " + topicid);
        mv.setViewName("nexthome");
        return mv;
    }

    @RequestMapping(value = "/users")
    public ModelAndView testModelAttribute(@ModelAttribute("model") UserModel model) {

        ModelAndView mv = new ModelAndView();
        return mv;
    }


}
