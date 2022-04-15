package kp.bahmatov.insurance.domain.dto.filter;

import kp.bahmatov.insurance.domain.structure.insurance.InsuranceStatus;
import kp.bahmatov.insurance.repo.specification.reflection.FilterIgnore;
import lombok.Data;


@Data
public class InsuranceFilterDto {
    private Long id;
    private Integer creator;
    @FilterIgnore
    private Integer userId;
    private String ownerFirstName;
    private String ownerSecondName;
    private String ownerPatronymic;
    private String winNumber;
    private String registrationNumber;
    @FilterIgnore
    private String passportId;
    @FilterIgnore
    private String phone;
    private InsuranceStatus status;
}
