package kp.bahmatov.insurance.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import kp.bahmatov.insurance.config.dataformat.ContentTypeConverter;
import kp.bahmatov.insurance.config.dataformat.DateConverter;
import kp.bahmatov.insurance.domain.structure.insurance.content.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;

@Configuration
public class JacksonFormatter {

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .serializers(
                        new DateConverter.JacksonDateSerializer(),
                        new ContentTypeConverter.JacksonContentTypeSerializer()
                )
                .deserializerByType(LocalDateTime.class, new DateConverter.JacksonDateDeserializer())
                .deserializerByType(ContentType.class, new ContentTypeConverter.JacksonContentTypeDeserializer())
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .propertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
    }
}
