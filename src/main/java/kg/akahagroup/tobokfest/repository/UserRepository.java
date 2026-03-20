package kg.akahagroup.tobokfest.repository;

import kg.akahagroup.tobokfest.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import kg.akahagroup.tobokfest.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRoles role);
}