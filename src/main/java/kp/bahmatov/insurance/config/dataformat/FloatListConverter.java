package kp.bahmatov.insurance.config.dataformat;


import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;

@Deprecated
public class FloatListConverter {
    public static final class HibernateFloatListConverter implements AttributeConverter<Iterable<Float>, String> {
        private static final char ITEMS_SEPARATOR = ';';


        @Override
        public String convertToDatabaseColumn(Iterable<Float> integers) {
            if (!integers.iterator().hasNext())
                return "";
            
            StringBuilder result = new StringBuilder();
            integers.forEach(item -> result.append(item).append(ITEMS_SEPARATOR));
            
            return result.deleteCharAt(result.length() - 1).toString();
        }

        @Override
        public Iterable<Float> convertToEntityAttribute(String s) {
            if (s.isBlank())
                return Collections.emptyList();

            String[] split = s.split(ITEMS_SEPARATOR + "");
            return Arrays.stream(split).map(Float::parseFloat).toList();
        }
    }
}
