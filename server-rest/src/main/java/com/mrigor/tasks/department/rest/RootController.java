package com.mrigor.tasks.department.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * root controller for start page (test page)
 */
@Controller
@Api(hidden = true)
public class RootController {
    private static final Logger LOG  = LoggerFactory.getLogger(RootController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "fd", hidden = true)
    public String root() {
        LOG.info("run REST server");
        return "index";
    }


}
