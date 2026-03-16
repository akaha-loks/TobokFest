package kg.akahagroup.tobokfest.repository;

import kg.akahagroup.tobokfest.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTitleContainingIgnoreCase(String title);
    List<Event> findByGenreIgnoreCase(String genre);
    List<Event> findByDateBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByVenueCityContainingIgnoreCase(String venueCity);
}