package kp.bahmatov.insurance.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DefaultStructureJsonParser {
    public static  <T> T parse(String filename, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (filename != null) {
            return objectMapper.readValue(DefaultStructureJsonParser.class.getClassLoader().getResource("default_structure/" + filename), clazz);
        }
        throw new NullPointerException("Filename is null");
    }
}
