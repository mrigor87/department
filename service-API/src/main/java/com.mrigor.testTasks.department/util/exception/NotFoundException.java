package com.mrigor.testTasks.department.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class describes exception when data is not found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No data found")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
