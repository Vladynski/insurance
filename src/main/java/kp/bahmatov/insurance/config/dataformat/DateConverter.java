package kp.bahmatov.insurance.config.dataformat;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import kp.bahmatov.insurance.util.StringDateUtil;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DateConverter {
    public static final class JacksonDateSerializer extends StdSerializer<LocalDateTime> {
        public JacksonDateSerializer() {
            super(LocalDateTime.class);
        }

        @Override
        public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(StringDateUtil.format(dateTime));
        }
    }

    public static final class JacksonDateDeserializer extends StdDeserializer<LocalDateTime> {
        public JacksonDateDeserializer() {
            super(LocalDateTime.class);
        }

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            return StringDateUtil.parse(jsonParser.readValueAs(String.class));
        }
    }

    public static final class HibernateListDateConverter implements AttributeConverter<List<LocalDateTime>, String> {
        private static final String ITEMS_SEPARATOR = ";";

        @Override
        public String convertToDatabaseColumn(List<LocalDateTime> ldtList) {
            if (!ldtList.iterator().hasNext())
                return "";
            
            StringBuilder result = new StringBuilder();
            for (LocalDateTime ldt : ldtList) {
                result.append(StringDateUtil.format(ldt)).append(ITEMS_SEPARATOR);
            }
            
            return result.deleteCharAt(result.length() - 1).toString();
        }

        @Override
        public List<LocalDateTime> convertToEntityAttribute(String s) {
            if (s.isBlank())
                return Collections.emptyList();

            String[] split = s.split(ITEMS_SEPARATOR);
            return Arrays.stream(split).map(StringDateUtil::parse).toList();
        }
    }
}
