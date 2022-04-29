package kp.bahmatov.insurance.config;

import kp.bahmatov.insurance.config.error.CustomAuthenticationHandler;
import kp.bahmatov.insurance.service.PrincipalService;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class Beans {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationHandler();
    }

    @Bean
    public Auth auth() {
        return new PrincipalService();
    }
}
