package com.mrigor.testTasks.department.web;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

//import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    DepartmentService departmentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        System.out.println("!!!!!!!!!!!!@@@@@@@@@@@@@@@!!!!!!!!!!!!!");
        return "index";
    }

    //go to main page Departments
    @GetMapping(value = "/departments")
    String departments() {

        return "departmentPage";
    }

    @GetMapping(value = "/employees/{departmentid}")
    String employees(Model model, @PathVariable("departmentid")int departmentid) {
        model.addAttribute("department",departmentService.get(departmentid));
        return "employeePageNew";
    }
    @GetMapping(value = "/employees/filtered")
    String employeesByDepartmentId(@RequestParam(value = "departmentid") Integer departmentId,
                                   Model model) {
        model.addAttribute("department",departmentService.get(departmentId));

        return "employeePageNew";
    }



    @GetMapping(value = "/employees/byDep")
    String employeesByDepartment() {

        return "employeePage";
    }

/*    @PostMapping("/department")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));;
        return "redirect:meals";
    }*/


}
