package kg.akahagroup.tobokfest.controller;

import kg.akahagroup.tobokfest.dto.request.AuthRequest;
import kg.akahagroup.tobokfest.dto.response.AuthResponse;
import kg.akahagroup.tobokfest.model.User;
import kg.akahagroup.tobokfest.repository.UserRepository;
import kg.akahagroup.tobokfest.security.JwtService;
import kg.akahagroup.tobokfest.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthController(
            AuthService authService,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            UserRepository userRepository
    ) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(Map.of("token", token));
    }
}