package com.hanzai.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class ObjectMapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // Enable or disable certain features based on requirements
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true); // Beautify the output
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Disable timestamps
    }

    /**
     * Convert an object to a JSON string.
     *
     * @param object The object to be converted
     * @return The JSON string
     */
    public static String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert object to JSON", e);
            return null;
        }
    }

    /**
     * Convert a JSON string to an object of the specified type.
     *
     * @param json  The JSON string
     * @param clazz The target type
     * @param <T>   The generic type of the target
     * @return The object of the target type
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("Failed to convert JSON to object", e);
            return null;
        }
    }

    /**
     * Converts a given JSON string to an object of type T.
     *
     * @param json The JSON string to convert
     * @param <T> The type of the resulting object
     * @return The converted object of type T, or null if conversion fails
     */
    public static <T> T jsonToObject(String json) {
        try {
            // Convert JSON string to the corresponding Java object
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error("Failed to convert JSON to object", e);
            return null; // Return null if conversion fails
        }
    }

    /**
     * Convert a JSON string to a Map.
     *
     * @param json The JSON string
     * @return A Map
     */
    public static Map<String, Object> jsonToMap(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            log.error("Failed to convert JSON to Map", e);
            return null;
        }
    }

    /**
     * Convert a JSON string to a JsonNode.
     *
     * @param json The JSON string
     * @return The JsonNode
     */
    public static JsonNode jsonToJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (IOException e) {
            log.error("Failed to convert JSON to JsonNode", e);
            return null;
        }
    }

    /**
     * Convert an object to a byte array.
     *
     * @param object The object to be converted
     * @return The byte array
     */
    public static byte[] objectToByteArray(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert object to byte array", e);
            return null;
        }
    }

    /**
     * Convert a byte array to an object.
     *
     * @param bytes The byte array
     * @param clazz The target type
     * @param <T>   The generic type of the target
     * @return The object of the target type
     */
    public static <T> T byteArrayToObject(byte[] bytes, Class<T> clazz) {
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            log.error("Failed to convert byte array to object", e);
            return null;
        }
    }
}
