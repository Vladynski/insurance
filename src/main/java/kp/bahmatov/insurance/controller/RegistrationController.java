package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.user.CreateUserDto;
import kp.bahmatov.insurance.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Value("${host}")
    private String host;

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping//, HttpServletResponse response
    public void createUser(@RequestBody CreateUserDto createUserDto, HttpServletResponse response) {
        userService.addUser(createUserDto);
        response.setStatus(HttpStatus.CREATED.value());
    }

    @GetMapping("/activate")
    public void activate(@PathParam("code") String code, HttpServletResponse response) {
        userService.activateUser(code);

        try {
                response.sendRedirect(host + "?message=" + URLEncoder.encode("Аккаунт активирован\uD83C\uDF8A", StandardCharsets.UTF_8) + "&type=ok");

        } catch (IOException e) {
            //FIXME add log
            throw new RuntimeException(e);
        }
    }
}
