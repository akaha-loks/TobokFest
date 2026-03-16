package kg.akahagroup.tobokfest.repository;

import kg.akahagroup.tobokfest.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}