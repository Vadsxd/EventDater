package ru.event.eventdater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.event.eventdater.domain.Event;
import ru.event.eventdater.domain.User;
import ru.event.eventdater.domain.enums.ContractStatus;
import ru.event.eventdater.domain.enums.Role;
import ru.event.eventdater.repos.EventRepo;
import ru.event.eventdater.repos.UserRepo;
import ru.event.eventdater.requests.SubscribeEventRequest;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final EventRepo eventRepo;

    @Autowired
    public UserService(UserRepo userRepo, EventRepo eventRepo) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    public void save(User user) {
        userRepo.save(user);
    }

    @Transactional
    public void create(User user) {
        if (userRepo.existsByLogin(user.getLogin())) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }

        save(user);
    }

    public User getByLogin(String login) {
        return userRepo.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByLogin;
    }

    public User getCurrentUser() {
        var login = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByLogin(login);
    }

    public User subscribeEvent(SubscribeEventRequest request) {
        Event event = eventRepo.findById(request.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Данного мероприятия не существует"));

        if (event.getContract().getContractStatus().equals(ContractStatus.NEOK)) {
            throw new RuntimeException("Контракт мероприятия не подписан");
        }

        User user = getCurrentUser();
        if (user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.PRINCIPAL)) {
            throw new RuntimeException("Только пользователь может принять участие в мероприятии");
        }

        user.setEvent(event);
        save(user);

        return user;
    }
}
