package kg.akahagroup.tobokfest.repository;

import kg.akahagroup.tobokfest.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
}