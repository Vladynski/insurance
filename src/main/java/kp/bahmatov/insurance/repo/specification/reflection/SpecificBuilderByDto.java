package kp.bahmatov.insurance.repo.specification.reflection;

import kp.bahmatov.insurance.repo.specification.SpecificationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class SpecificBuilderByDto {
    private static final Logger logger = LoggerFactory.getLogger(SpecificBuilderByDto.class);

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
                    logger.error("Ошибка при автоматическом создании запроса фильтра для БД:\n{0}", e);
                    //на совести разработчика, если имена полей dto такие же, как у самой структуры,
                    //и сами поля не static и/или final, то это исключение никогда не вылетит
                }
            }
        }
    }
}
