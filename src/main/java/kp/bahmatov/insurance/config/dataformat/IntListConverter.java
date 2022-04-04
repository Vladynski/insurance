package kp.bahmatov.insurance.config.dataformat;


import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class IntListConverter {
    public static final class HibernateIntListConverter implements AttributeConverter<Iterable<Integer>, String> {
        private static final char ITEMS_SEPARATOR = ';';


        @Override
        public String convertToDatabaseColumn(Iterable<Integer> integers) {
            if (!integers.iterator().hasNext())
                return "";
            
            StringBuilder result = new StringBuilder();
            integers.forEach(item -> result.append(item).append(ITEMS_SEPARATOR));
            
            return result.deleteCharAt(result.length() - 1).toString();
        }

        @Override
        public Iterable<Integer> convertToEntityAttribute(String s) {
            if (s.isBlank())
                return Collections.emptyList();

            String[] split = s.split(ITEMS_SEPARATOR + "");
            return Arrays.stream(split).map(Integer::parseInt).toList();
        }
    }
}
