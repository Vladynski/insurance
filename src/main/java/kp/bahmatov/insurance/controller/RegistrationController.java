package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.user.CreateUserInDto;
import kp.bahmatov.insurance.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/registration")
@Validated
public class RegistrationController {
    @Value("${host}")
    private String host;

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody CreateUserInDto createUserDto) {
        userService.addUser(createUserDto);
    }

    @GetMapping("/activate")
    public void activate(@RequestParam("code") String code, HttpServletResponse response) {
        userService.activateUser(code);

        try {
            response.sendRedirect(host + "?message=" + URLEncoder.encode("Аккаунт активирован\uD83C\uDF8A", StandardCharsets.UTF_8) + "&type=ok");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
