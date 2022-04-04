package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.user.SelfUserOutDto;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.service.UserService;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final Auth auth;

    public UserController(UserService userService, Auth Auth) {
        this.userService = userService;
        this.auth = Auth;
    }


    @GetMapping
    public Iterable<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/self")
    public SelfUserOutDto getSelfData() {
        return new SelfUserOutDto(auth.getUser());
    }
}
