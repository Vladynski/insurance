package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.service.SettingsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
public class SettingsController {
    @GetMapping("/mailSendTimeoutSeconds")
    public int getMailSendTimeout() {
        return SettingsService.getMailSendTimeoutSeconds().getIntValue();
    }
}
