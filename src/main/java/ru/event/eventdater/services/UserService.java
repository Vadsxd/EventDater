package ru.event.eventdater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.event.eventdater.domain.User;
import ru.event.eventdater.repos.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    @Transactional
    public User create(User user) {
        if (userRepo.existsByLogin(user.getLogin())) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }

        return save(user);
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
}
