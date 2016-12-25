package com.mrigor.testTasks.department.util.converter;

import com.mrigor.testTasks.department.util.TimeUtil;
import org.springframework.format.Formatter;


import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * gkislin
 * 25.10.2016
 */
public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return TimeUtil.parseLocalDate(text);
        }

        @Override
        public String print(LocalDate lt, Locale locale) {

            return lt.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }


}
