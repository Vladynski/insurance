package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.insurance.userdata.InsuranceUserDataDto;
import kp.bahmatov.insurance.service.InsuranceUserDataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/insuranceUserData")
public class InsuranceUserDataController {
    private final InsuranceUserDataService insuranceUserDataService;

    public InsuranceUserDataController(InsuranceUserDataService insuranceUserDataService) {
        this.insuranceUserDataService = insuranceUserDataService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void init(@Valid @RequestBody InsuranceUserDataDto requestInsurance) {
        insuranceUserDataService.updateInsuranceData(requestInsurance);
    }
}
