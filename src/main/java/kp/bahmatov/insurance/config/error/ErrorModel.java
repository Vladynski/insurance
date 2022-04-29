package kp.bahmatov.insurance.config.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorModel {
    private String message;
    private LocalDateTime time;

    public ErrorModel(String message) {
        this.message = message;
        time = LocalDateTime.now();
    }
}
