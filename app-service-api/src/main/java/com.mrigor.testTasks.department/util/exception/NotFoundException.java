package com.mrigor.testTasks.department.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Игорь on 21.12.2016.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No data found")  // 404
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {

        super(message);
    }
}
