package kg.akahagroup.tobokfest.service;

import kg.akahagroup.tobokfest.dto.request.AuthRequest;
import kg.akahagroup.tobokfest.dto.response.AuthResponse;
import kg.akahagroup.tobokfest.model.User;
import kg.akahagroup.tobokfest.repository.UserRepository;
import kg.akahagroup.tobokfest.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(token);
    }
}