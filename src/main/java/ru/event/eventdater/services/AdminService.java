package ru.event.eventdater.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.event.eventdater.domain.Contract;
import ru.event.eventdater.domain.Event;
import ru.event.eventdater.domain.User;
import ru.event.eventdater.domain.enums.ContractStatus;
import ru.event.eventdater.domain.enums.Role;
import ru.event.eventdater.repos.ContractRepo;
import ru.event.eventdater.repos.EventRepo;
import ru.event.eventdater.repos.UserRepo;
import ru.event.eventdater.requests.admin.CreateEventRequest;

@Service
public class AdminService {
    private final UserRepo userRepo;
    private final EventRepo eventRepo;
    private final ContractRepo contractRepo;
    private final UserService userService;

    @Autowired
    public AdminService(UserRepo userRepo, EventRepo eventRepo, ContractRepo contractRepo, UserService userService) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.contractRepo = contractRepo;
        this.userService = userService;
    }

    public User givePrincipalRole(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Такой пользователь не зарегистрирован"));

        user.setRole(Role.PRINCIPAL);
        userService.save(user);

        return user;
    }

    @Transactional
    public Event createEvent(CreateEventRequest request) {
        var event = Event.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();

        eventRepo.save(event);

        var contract = Contract.builder()
                .contractStatus(ContractStatus.NEOK)
                .event(event)
                .build();

        contractRepo.save(contract);

        return event;
    }
}
