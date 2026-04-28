package kg.akahagroup.tobokfest.controller;

import kg.akahagroup.tobokfest.dto.request.AuthRequest;
import kg.akahagroup.tobokfest.dto.response.AuthResponse;
import kg.akahagroup.tobokfest.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}