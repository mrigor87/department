package com.mrigor.tasks.department.matcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.util.List;


/**
 * This util class helps converting json-data to entity using Jackson
 */
public class JsonUtil {

    private static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * convert json-data to list of entitys
     *
     * @param json  data in json format
     * @param clazz class of entities
     * @param <T>   entity
     * @return list of entity
     * @throws IllegalArgumentException
     */
    public static <T> List<T> readValues(String json, Class<T> clazz) {
        ObjectReader reader = MAPPER.readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }

    /**
     * convert json-data to entity
     *
     * @param json  data in json format
     * @param clazz class of entities
     * @param <T>   entity
     * @return entity
     * @throws IllegalArgumentException
     */
    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    /**
     * convert entity to json
     * @param obj data in json format
     * @param <T> entity
     * @return json data in String
     */
    public static <T> String writeValue(T obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }
}
