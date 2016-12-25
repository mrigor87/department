package com.mrigor.testTasks.department.web;


import com.mrigor.testTasks.department.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

//import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by Игорь on 17.12.2016.
 */
@Controller
public class RootController {
    private static final Logger LOG = LoggerFactory.getLogger(RootController.class);

    @Autowired
    DepartmentService departmentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        //test connection
        departmentService.getAll();
        LOG.debug("redirect to department list");
        return "departmentPage";
    }

    //go to main page Departments
    @GetMapping(value = "/departments")
    String departments(){
            LOG.debug("go to department's jsp-page");
        return "departmentPage";
    }

    @GetMapping(value = "/department/{departmentid}/employees")
    String employees(Model model, @PathVariable("departmentid")int departmentid) {
        LOG.debug("go to employee's jsp-page, departmentId={}",departmentid);
        model.addAttribute("department",departmentService.get(departmentid));
        return "employeePage";
    }





}
