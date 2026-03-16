package kg.akahagroup.tobokfest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kg.akahagroup.tobokfest.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}