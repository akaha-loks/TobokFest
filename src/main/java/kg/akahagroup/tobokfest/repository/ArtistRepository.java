package kg.akahagroup.tobokfest.repository;

import kg.akahagroup.tobokfest.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findByNameContainingIgnoreCase(String name);

    List<Artist> findBySurnameContainingIgnoreCase(String surname);

    List<Artist> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(
            String name,
            String surname
    );
}