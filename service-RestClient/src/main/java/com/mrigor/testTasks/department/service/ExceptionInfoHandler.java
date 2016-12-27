package com.mrigor.testTasks.department.service;

import com.mrigor.testTasks.department.util.exception.ErrorInfo;
import com.mrigor.testTasks.department.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Игорь on 21.12.2016.
 */
@ControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {
    Logger LOG = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ExceptionHandler(Exception.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorInfo handleError(HttpServletResponse req, Exception e) {

        LOG.error("Exception at request " + req.toString(), e);
        return new ErrorInfo(req.toString(), e);
    }

}
