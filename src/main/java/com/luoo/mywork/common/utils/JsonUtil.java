package com.luoo.mywork.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by caohx on 2016/3/8.
 */
public class JsonUtil {
    protected static ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String toJson(Object object)throws JsonProcessingException{
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T toObject(String json, Class<T> cls) {
        try {
            return objectMapper.readValue(json, cls);
        } catch (Exception e) {
            throw new RuntimeException("error decode json to " + cls, e);
        }
    }
}
