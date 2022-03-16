package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.user.SelfUserDataDto;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public Iterable<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/self")
    public SelfUserDataDto getSelfData(Principal principal) {
        return new SelfUserDataDto((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal());
    }
}
