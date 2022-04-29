package kp.bahmatov.insurance;

import kp.bahmatov.insurance.service.SettingsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InsuranceApplication {
	public static void main(String[] args) {
		SpringApplication.run( new Class[]{SettingsService.class, InsuranceApplication.class}, args);
	}
}
