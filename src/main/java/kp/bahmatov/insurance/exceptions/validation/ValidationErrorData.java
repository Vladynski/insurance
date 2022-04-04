package kp.bahmatov.insurance.exceptions.validation;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ValidationErrorData {
    private final Map<String, String> data = new LinkedHashMap<>();

    public void addItem(String fieldName, String message) {
        this.data.put(fieldName, message);
    }
}
