package com.mrigor.testTasks.department.web;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
/*        List<Department> all = departmentService.getAll();
        model.addAttribute("departmentList", all);*/
        return "departmentPage";
    }

    @GetMapping(value = "/employees")
    String employees() {
/*        List<Department> all = departmentService.getAll();
        model.addAttribute("departmentList", all);*/
        return "employeePage";
    }
    @GetMapping(value = "/employees/department/{departId}")
    String employeesByDepartmentId(@PathVariable("departId")int departId, Model model) {
        model.addAttribute("department",departmentService.get(departId));
/*        List<Department> all = departmentService.getAll();
        model.addAttribute("departmentList", all);*/
        return "employeePageNew";
    }


    @GetMapping(value = "/employees/byDep")
    String employeesByDepartment() {
/*        List<Department> all = departmentService.getAll();
        model.addAttribute("departmentList", all);*/
        return "employeePage";
    }

/*    @PostMapping("/department")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));;
        return "redirect:meals";
    }*/


}
