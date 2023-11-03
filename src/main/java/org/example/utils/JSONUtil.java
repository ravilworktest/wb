package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
    ObjectMapper objectMapper = new ObjectMapper();

    public <T> T  jsonToObject(String json, Class<T> tClass){

        try {

            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            System.out.println("DATA: " + json);
            System.out.println("RuntimeException: " + e);
            return null;
        }
    }
}
