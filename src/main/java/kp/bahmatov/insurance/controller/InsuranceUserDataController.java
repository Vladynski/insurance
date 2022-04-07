package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.edit.EditInsuranceDataDto;
import kp.bahmatov.insurance.domain.dto.insurance.userdata.InsuranceUserDataDto;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserData;
import kp.bahmatov.insurance.service.InsuranceUserDataService;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public void edit(
            @RequestHeader("password") String password,
            @Valid @RequestBody EditInsuranceDataDto editInsuranceDataDto) {
        insuranceUserDataService.updateInsuranceData(editInsuranceDataDto, password);
    }
}
