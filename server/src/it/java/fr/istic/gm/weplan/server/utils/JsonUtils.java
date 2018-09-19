package fr.istic.gm.weplan.server.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Parse object in Json
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

    public static <T> T parseToList(String json) {
        try {
            return MAPPER.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseToObject(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String parseToJson(T object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parse(String json, Class<T> type) {
        if (json != null && !json.equals("")) {
            if (json.startsWith("[")) {
                return JsonUtils.parseToList(json);
            } else if (type != null) {
                return JsonUtils.parseToObject(json, type);
            }
        }
        return null;
    }

    public static Object fromJson(MvcResult mvcResult, Class clazz) throws Exception {
        String json = mvcResult.getResponse().getContentAsString();
        return fromJson(json, clazz);
    }

    public static Object fromJson(String json, Class clazz) throws Exception {
        return MAPPER.readValue(json, clazz);
    }

    public static Object fromJson(InputStream inputStream, Class clazz) throws IOException {
        return MAPPER.readValue(inputStream, clazz);
    }
}
