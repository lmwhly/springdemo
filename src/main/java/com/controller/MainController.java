package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dzkan on 2015/10/3.
 */
@Controller
public class MainController {

    @RequestMapping(value = "/ok", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

}
