package kp.bahmatov.insurance.repo.specification.reflection;

import kp.bahmatov.insurance.repo.specification.SpecificationBuilder;

import java.lang.reflect.Field;

public class SpecificBuilderByDto {
    public static <T> void fillingBuilder(SpecificationBuilder<T> builder, Object dto) {
        Field[] declaredFields = dto.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getAnnotation(FilterIgnore.class) == null) {
                field.setAccessible(true);
                try {
                    Object value = field.get(dto);
                    if (value != null)
                        builder.with(field.getName(), value);
                } catch (IllegalAccessException e) {
                    //FIXME
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
