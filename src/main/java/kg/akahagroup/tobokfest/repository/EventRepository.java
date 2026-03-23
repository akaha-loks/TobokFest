package kg.akahagroup.tobokfest.repository;

import kg.akahagroup.tobokfest.model.Event;
import kg.akahagroup.tobokfest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTitleContainingIgnoreCase(String title);
    List<Event> findByGenreIgnoreCase(String genre);
    List<Event> findByDateBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByVenueCityContainingIgnoreCase(String venueCity);
    List<Event> findByOwner(User owner);

    @Query("""
    SELECT e FROM Event e
    WHERE (:title IS NULL OR LOWER(e.title) LIKE LOWER(CONCAT('%', :title, '%')))
    AND (:genre IS NULL OR LOWER(e.genre) = LOWER(:genre))
    AND (:city IS NULL OR LOWER(e.venue.city) LIKE LOWER(CONCAT('%', :city, '%')))
    AND (:start IS NULL OR e.date >= :start)
    AND (:end IS NULL OR e.date <= :end)
    """)
    List<Event> findEvents(
            String title,
            String genre,
            String city,
            LocalDateTime start,
            LocalDateTime end
    );
}