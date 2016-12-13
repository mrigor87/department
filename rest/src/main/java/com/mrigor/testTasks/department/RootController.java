package com.mrigor.testTasks.department;

import org.springframework.beans.factory.annotation.Autowired;
;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        System.out.println("HURAAAAAAAAAAAAAAAAAAAAAA");
        return "index";
    }


}
