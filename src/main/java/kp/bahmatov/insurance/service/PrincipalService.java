package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalService implements Auth {
    @Override
    public User getUser() {
        return (User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
