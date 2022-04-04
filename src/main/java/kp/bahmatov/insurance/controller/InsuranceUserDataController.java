package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.insurance.userdata.InsuranceUserDataDto;
import kp.bahmatov.insurance.service.InsuranceUserDataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/insuranceUserData")
public class InsuranceUserDataController {
    private final InsuranceUserDataService insuranceUserDataService;

    public InsuranceUserDataController(InsuranceUserDataService insuranceUserDataService) {
        this.insuranceUserDataService = insuranceUserDataService;
    }

    @PostMapping("/update")
    private void sendRequest(@Valid @RequestBody InsuranceUserDataDto requestInsurance) {
        insuranceUserDataService.updateInsuranceData(requestInsurance);
    }
}
