package ru.event.eventdater.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.event.eventdater.domain.Event;
import ru.event.eventdater.domain.User;
import ru.event.eventdater.requests.admin.CreateEventRequest;
import ru.event.eventdater.services.AdminService;

@RequestMapping("/api/admin")
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Ошибка авторизации"),
            @ApiResponse(responseCode = "404", description = "Данный пользователь не зарегистрирован")
    })
    @Operation(summary = "Выдать роль PRINCIPAL пользователю")
    @PutMapping("/givePrincipal/{id}")
    public ResponseEntity<User> givePrincipalRole(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.givePrincipalRole(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Ошибка авторизации")
    })
    @Operation(summary = "Создать мероприятие")
    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(@RequestBody @Valid CreateEventRequest request) {
        return ResponseEntity.ok(adminService.createEvent(request));
    }
}
