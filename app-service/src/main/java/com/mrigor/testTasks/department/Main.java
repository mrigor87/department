package com.mrigor.testTasks.department;

//import com.mrigor.testTasks.department.repository.DepartmentRepoImpl;

import com.mrigor.testTasks.department.model.Department;
import com.mrigor.testTasks.department.repository.DepartmentRepo;
import com.mrigor.testTasks.department.repository.DepartmentRepoImpl;
import com.mrigor.testTasks.department.service.DepartmentService;
import com.mrigor.testTasks.department.service.DepartmentServiceImpl;
import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Игорь on 10.12.2016.
 */
public class Main {
    private static final Logger LOG = getLogger(Main.class);
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml")) {


            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
           // DepartmentRepo dep=appCtx.getBean(DepartmentRepoImpl.class);
            //List<Department> all = dep.getAll();
            String[] beanDefinitionNames = appCtx.getBeanDefinitionNames();
            for (String string : appCtx.getBeanDefinitionNames()) {
                System.out.println(string);
            }

/*            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(UserTestData.USER);
            System.out.println();

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);*/
        }
    }
}
