package ru.event.eventdater.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.event.eventdater.domain.User;
import ru.event.eventdater.requests.SubscribeEventRequest;
import ru.event.eventdater.services.UserService;

@RequestMapping("/api/user")
@RestController
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Ошибка авторизации"),
            @ApiResponse(responseCode = "404", description = "Данного мероприятия не существует")
    })
    @Operation(summary = "Записаться на мероприятие")
    @PutMapping("/subscribeEvent")
    public ResponseEntity<User> subscribeEvent(@RequestBody @Valid SubscribeEventRequest request) {
        return ResponseEntity.ok(userService.subscribeEvent(request));
    }
}
