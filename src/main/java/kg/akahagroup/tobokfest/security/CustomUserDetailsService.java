package kg.akahagroup.tobokfest.security;

import kg.akahagroup.tobokfest.model.User;
import kg.akahagroup.tobokfest.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)  // ✅ было findByUsername
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + email));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())   // ✅ важно: username = email
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}