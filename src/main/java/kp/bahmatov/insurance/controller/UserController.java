package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.edit.EditUserDataDto;
import kp.bahmatov.insurance.domain.dto.user.SelfUserOutDto;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.service.UserService;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PutMapping
    public void edit(
            @RequestHeader(value = "password", required = false) String password,
            @Valid @RequestBody EditUserDataDto editInsuranceDataDto) {
        this.userService.updateUserData(editInsuranceDataDto, password);
    }
}
