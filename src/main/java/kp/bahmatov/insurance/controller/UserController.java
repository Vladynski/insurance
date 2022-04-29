package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.SendMailInDto;
import kp.bahmatov.insurance.domain.dto.edit.EditUserDataDto;
import kp.bahmatov.insurance.domain.dto.filter.UserFilter;
import kp.bahmatov.insurance.domain.dto.user.AdminUserDataDto;
import kp.bahmatov.insurance.domain.dto.user.SelfUserOutDto;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Iterable<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/self")
    public SelfUserOutDto getSelfData() {
        return new SelfUserOutDto(userService.getSelfData(), userService.getInsuranceCountForUser(null));
    }

    @PutMapping
    public void edit(
            @RequestHeader(value = "password", required = false) String password,
            @Valid @RequestBody EditUserDataDto editInsuranceDataDto) {
        this.userService.updateUserData(editInsuranceDataDto, password);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AdminUserDataDto> filter(@Valid @RequestBody UserFilter userFilter) {
        return userService.filter(userFilter).stream().map(AdminUserDataDto::new).toList();
    }

    @PostMapping("/sendMail")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void sendMail(@Valid @RequestBody SendMailInDto sendMailDto) {
        userService.sendMail(sendMailDto.getId(), sendMailDto.getText());
    }

    @Deprecated
    @PutMapping("/ban")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void ban(@RequestParam("id") Integer userId) {
        userService.ban(userId);
    }
}
