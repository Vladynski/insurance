package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.insurance.userdata.InsuranceUserDataDto;
import kp.bahmatov.insurance.service.InsuranceUserDataService;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/insuranceUserData")
public class InsuranceUserDataController {
    private final InsuranceUserDataService insuranceUserDataService;
    private final Auth auth;

    public InsuranceUserDataController(InsuranceUserDataService insuranceUserDataService, Auth auth) {
        this.insuranceUserDataService = insuranceUserDataService;
        this.auth = auth;
    }

    @PostMapping
    private void init(@Valid @RequestBody InsuranceUserDataDto requestInsurance) {
        insuranceUserDataService.updateInsuranceData(requestInsurance);
    }
}
