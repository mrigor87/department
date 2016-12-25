package com.mrigor.testTasks.department.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Игорь on 17.12.2016.
 */
public class TimeUtil {


    private TimeUtil() {
    }

    public static LocalDate parseLocalDate(String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }



}