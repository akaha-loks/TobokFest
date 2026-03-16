package kg.akahagroup.tobokfest.config;

import kg.akahagroup.tobokfest.enums.UserRoles;
import kg.akahagroup.tobokfest.model.User;
import kg.akahagroup.tobokfest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@tobokfest.kg").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@tobokfest.kg");
            admin.setUsername("Admin");
            admin.setPassword(passwordEncoder.encode("Vg4@0bPsamP8ZY5"));
            admin.setRole(UserRoles.ADMIN);
            userRepository.save(admin);
        }
    }
}
