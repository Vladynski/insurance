package kp.bahmatov.insurance.config.dataformat;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import kp.bahmatov.insurance.domain.structure.insurance.content.ContentType;

import java.io.IOException;

public class ContentTypeConverter {
    public static final class JacksonContentTypeSerializer extends StdSerializer<ContentType> {
        public JacksonContentTypeSerializer() {
            super(ContentType.class);
        }

        @Override
        public void serialize(ContentType contentType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(contentType.toString());
        }
    }

    public static final class JacksonContentTypeDeserializer extends StdDeserializer<ContentType> {
        public JacksonContentTypeDeserializer() {
            super(ContentType.class);
        }

        @Override
        public ContentType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            return ContentType.findByString(jsonParser.readValueAs(String.class));
        }
    }
}
