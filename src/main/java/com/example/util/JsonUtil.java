package com.example.util;

import com.example.exceptionHandling.CustomRuntimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.SQLException;

public class JsonUtil {
    public static PGobject writeAsJsonString(ObjectMapper objectMapper, Object object) {
        try {
            String jsonString = objectMapper.writeValueAsString(object);
            PGobject jsonbObject = new PGobject();
            jsonbObject.setType("jsonb");
            jsonbObject.setValue(jsonString);
            return jsonbObject;
        } catch (IOException | SQLException e) {
            throw new CustomRuntimeException(
                    "JSON_WRITING_ERROR",
                    "Failed to write object to JSON string"
            );
        }
    }

    public static <T> T readJson(ObjectMapper objectMapper, String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new CustomRuntimeException(
                    "JSON_READ_ERROR",
                    "Failed to read JSON"
            );
        }
    }
}
