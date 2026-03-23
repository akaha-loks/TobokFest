package kg.akahagroup.tobokfest.service;

import kg.akahagroup.tobokfest.dto.request.UserRequest;
import kg.akahagroup.tobokfest.dto.response.UserResponse;
import kg.akahagroup.tobokfest.enums.UserRoles;
import kg.akahagroup.tobokfest.exception.ResourceNotFoundException;
import kg.akahagroup.tobokfest.model.User;
import kg.akahagroup.tobokfest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> getAdminsAndOrganizers() {
        return userRepository.findByRoleIn(List.of(UserRoles.ADMIN, UserRoles.ORGANIZER))
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    public List<UserResponse> getOrganizers() {
        return userRepository.findByRole(UserRoles.ORGANIZER)
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    public UserResponse createOrganizer(UserRequest request) {
        userRepository.findByEmail(request.email())
                .ifPresent(u -> { throw new IllegalArgumentException("Email already in use"); });

        userRepository.findByUsername(request.username())
                .ifPresent(u -> { throw new IllegalArgumentException("Username already in use"); });

        User organizer = new User();
        organizer.setUsername(request.username());
        organizer.setEmail(request.email());
        organizer.setPassword(passwordEncoder.encode(request.password()));
        organizer.setRole(UserRoles.ORGANIZER);

        User saved = userRepository.save(organizer);

        return UserResponse.from(saved);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserResponse.from(user);
    }
}