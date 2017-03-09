package com.mrigor.tasks.department.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Exception handling using Spring annotation @ControllerAdvice
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        StringBuilder message=new StringBuilder( e.getMessage());
        if (e instanceof RestClientResponseException) {
            RestClientResponseException exceptionWithResponse = (RestClientResponseException) e;
            message.append(", response:"+exceptionWithResponse.getResponseBodyAsString());
        }

        LOG.error("Exception at request " + req.getRequestURL(), message);
        ModelAndView mav = new ModelAndView("exception/exception");

        mav.addObject("exception", message);


        return mav;
    }
}
