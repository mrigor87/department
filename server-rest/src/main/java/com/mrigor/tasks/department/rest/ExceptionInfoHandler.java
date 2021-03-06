package com.mrigor.tasks.department.rest;

import com.mrigor.tasks.department.util.exception.ErrorInfo;
import com.mrigor.tasks.department.util.exception.NotFoundException;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Igor on 21.12.2016.
 */
@ControllerAdvice(annotations = RestController.class)
@Api (value = "ErrorHandler")
public class ExceptionInfoHandler {
    Logger LOG = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        LOG.error("Exception at request " + req.getRequestURL(), e);
        return new ErrorInfo(req.getRequestURL(), e);
    }

}
