package kg.akahagroup.tobokfest.controller;

import kg.akahagroup.tobokfest.dto.request.AuthRequest;
import kg.akahagroup.tobokfest.dto.response.AuthResponse;
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

    public AuthController(
            AuthService authService,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String token = jwtService.generateToken(request.email());

        return ResponseEntity.ok(Map.of("token", token));
    }
}