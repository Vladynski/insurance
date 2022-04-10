package kp.bahmatov.insurance.domain.dto.filter;

import kp.bahmatov.insurance.repo.specification.reflection.FilterIgnore;
import lombok.Data;

@Data
public class UserFilter {
    private String firstName;
    private String secondName;
    private String patronymic;
    private String email;
    private String passportId;
    private Integer id;
    private Integer insuranceData;
    @FilterIgnore
    private Boolean admin;
}
