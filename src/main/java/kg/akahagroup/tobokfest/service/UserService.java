package kg.akahagroup.tobokfest.service;

import kg.akahagroup.tobokfest.enums.UserRoles;
import kg.akahagroup.tobokfest.model.User;
import kg.akahagroup.tobokfest.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getOrganizers() {
        return userRepository.findByRole(UserRoles.ORGANIZER);
    }

    public User createOrganizer(String username, String email, String password, Long adminId){
        User admin = userRepository.findById(adminId).orElseThrow(() -> new RuntimeException("admin not found"));

        if(admin.getRole() != UserRoles.ADMIN){
            throw new RuntimeException("Admin role not allowed");
        }

        User organizer = new User();
        organizer.setUsername(username);
        organizer.setEmail(email);
        organizer.setPassword(passwordEncoder.encode(password));
        organizer.setRole(UserRoles.ORGANIZER);

        return userRepository.save(organizer);
    }
}
