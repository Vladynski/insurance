package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.SettingsDto;
import kp.bahmatov.insurance.service.SettingsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/settings")
public class SettingsController {
    @GetMapping("/mailSendTimeoutSeconds")
    public int getMailSendTimeout() {
        return SettingsService.getMailSendTimeoutSeconds().getIntValue();
    }

    @GetMapping("/insuranceContractText")
    public String getInsuranceContractText() {
        return SettingsService.getInsuranceContractText().getValue();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SettingsDto> getSettings() {
        return SettingsService.getAll().stream().map(SettingsDto::new).toList();
    }

    @PutMapping
    public void updateSetting(@Valid @RequestBody SettingsDto settingsDto) {
        SettingsService.updateSetting(settingsDto);
    }
}
