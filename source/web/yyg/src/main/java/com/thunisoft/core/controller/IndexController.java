package com.thunisoft.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    private Logger logger  = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/login.html")
    public String login() {
        return "admin/login";
    }

    /*
     * @Author coonchen
     * @Description 测试
     * @Date 2018/11/22 0022 
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/index.html")
    public String index() {
        return "admin/index";
    }
}



