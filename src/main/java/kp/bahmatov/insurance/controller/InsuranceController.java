package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.insurance.InsuranceInDto;
import kp.bahmatov.insurance.service.ConfirmerService;
import kp.bahmatov.insurance.service.InsuranceService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {
    private final ConfirmerService confirmerService;
    private final InsuranceService insuranceService;

    public InsuranceController(ConfirmerService confirmerService,
                               InsuranceService insuranceService) {
        this.confirmerService = confirmerService;
        this.insuranceService = insuranceService;
    }

    @PostMapping
    public void createInsurance(@RequestParam(defaultValue = "") String code,
                                @Valid @RequestBody InsuranceInDto insuranceDto) {
        confirmerService.throwExceptionIfItIsNotAuthUserCode(code);
        insuranceService.createInsurance(insuranceDto);
    }

    @PutMapping("/valid")
    public void valid(@Valid @RequestBody InsuranceInDto insuranceDto) {
        insuranceService.throwExceptionIfInsuranceAlreadyExists(insuranceDto);
    }
}