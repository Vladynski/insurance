package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.edit.EditUserDataDto;
import kp.bahmatov.insurance.domain.dto.filter.UserFilter;
import kp.bahmatov.insurance.domain.dto.user.AdminUserDataDto;
import kp.bahmatov.insurance.domain.dto.user.SelfUserOutDto;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.repo.specification.SpecificationBuilder;
import kp.bahmatov.insurance.repo.specification.StructureSpecification;
import kp.bahmatov.insurance.service.UserService;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        return new SelfUserOutDto(userService.getSelfData());
    }

    @PutMapping
    public void edit(
            @RequestHeader(value = "password", required = false) String password,
            @Valid @RequestBody EditUserDataDto editInsuranceDataDto) {
        this.userService.updateUserData(editInsuranceDataDto, password);
    }

    //FIXME admin
    @PostMapping("/filter")
    public List<AdminUserDataDto> filter(@Valid @RequestBody UserFilter userFilter) {
        return userService.filter(userFilter).stream().map(AdminUserDataDto::new).toList();
    }

    @PutMapping("/ban{userId}")
    public void ban(@PathVariable Integer userId) {
        userService.ban(userId);
    }
}
