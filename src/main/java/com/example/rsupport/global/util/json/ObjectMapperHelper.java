package com.example.rsupport.global.util.json;


import com.example.rsupport.global.support.date.LocalDateDeserializer;
import com.example.rsupport.global.support.date.LocalDateSerializer;
import com.example.rsupport.global.support.date.LocalDateTimeDeserializer;
import com.example.rsupport.global.support.date.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ObjectMapperHelper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new ParameterNamesModule())
            .registerModule(module())
            .findAndRegisterModules()
            .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static SimpleModule module() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

        return module;
    }
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * String -> JSON
     * */
    public static <T> T fromJson(String str, TypeReference type) {
        try {
            return (T) OBJECT_MAPPER.readValue(str, type);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * String -> JSON
     * */
    public static <T> T fromJson(String str, Class<T> clz) {
        try {
            return OBJECT_MAPPER.readValue(str, clz);
        }  catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * JSON -> String
     * */
    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        }  catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static <T> T fromJson(String str, Type type) {
        try {
            return (T) OBJECT_MAPPER.readValue(str, (JavaType) type);
        }  catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static <T> T fromJson(String str, ParameterizedTypeReference<T> type) {
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructType(type.getType());
            return (T) OBJECT_MAPPER.readValue(str, javaType);
        }  catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
