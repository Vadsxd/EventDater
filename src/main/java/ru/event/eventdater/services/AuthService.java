package ru.event.eventdater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.event.eventdater.domain.User;
import ru.event.eventdater.domain.enums.Role;
import ru.event.eventdater.requests.auth.AuthRequest;
import ru.event.eventdater.responses.AuthResponse;
import ru.event.eventdater.security.JwtProvider;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            UserService userService,
            JwtProvider jwtProvider,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public AuthResponse signUp(AuthRequest request) {

        var user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userService.create(user);

        var jwt = jwtProvider.generateToken(user);
        return new AuthResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public AuthResponse signIn(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getLogin());

        var jwt = jwtProvider.generateToken(user);
        return new AuthResponse(jwt);
    }
}
