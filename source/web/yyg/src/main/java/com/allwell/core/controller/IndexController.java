package com.allwell.core.controller;

import com.allwell.core.exception.OperationException;
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

    @RequestMapping("/index.html")
    public String index() {
        //throw new OperationException(500,"我错了");
        //int k = 5/0;

        return "admin/index";
    }
}