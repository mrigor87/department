package com.mrigor.testTasks.department.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

;


/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        System.out.println("!!!!!!!!!!!!@@@@@@@@@@@@@@@!!!!!!!!!!!!!");
        return "index";
    }


}
