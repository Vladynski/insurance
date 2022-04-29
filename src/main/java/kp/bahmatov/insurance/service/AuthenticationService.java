package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    private final UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return userService.findByEmail(email);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
    }
}
