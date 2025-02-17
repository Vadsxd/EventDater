package ru.event.eventdater.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.event.eventdater.requests.auth.AuthRequest;
import ru.event.eventdater.responses.AuthResponse;
import ru.event.eventdater.services.AuthService;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public AuthResponse signUp(@RequestBody @Valid AuthRequest request) {
        return authService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public AuthResponse signIn(@RequestBody @Valid AuthRequest request) {
        return authService.signIn(request);
    }
}
