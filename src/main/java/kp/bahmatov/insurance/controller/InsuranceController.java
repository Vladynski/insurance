package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.filter.InsuranceFilterDto;
import kp.bahmatov.insurance.domain.dto.insurance.InsuranceInDto;
import kp.bahmatov.insurance.domain.dto.insurance.InsuranceOutDto;
import kp.bahmatov.insurance.domain.structure.insurance.Insurance;
import kp.bahmatov.insurance.service.ConfirmerService;
import kp.bahmatov.insurance.service.InsuranceService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping
    public List<InsuranceOutDto> getAllMy() {
        List<Insurance> allMy = insuranceService.getAllMy();
        return allMy.stream().map(InsuranceOutDto::new).toList();
    }

    //FIXME admin only
    @PostMapping("/filter")
    public List<InsuranceOutDto> filter(@Valid @RequestBody InsuranceFilterDto filterDto) {
        return insuranceService.filter(filterDto).stream().map(InsuranceOutDto::new).toList();
    }
}