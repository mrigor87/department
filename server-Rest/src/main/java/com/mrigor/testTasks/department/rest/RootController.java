package com.mrigor.testTasks.department.rest;

;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by Игорь on 11.12.2016.
 */
@Controller
public class RootController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {

        return "index";
    }


}
