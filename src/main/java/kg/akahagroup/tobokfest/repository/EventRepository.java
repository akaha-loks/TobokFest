package kg.akahagroup.tobokfest.repository;

import kg.akahagroup.tobokfest.model.Event;
import kg.akahagroup.tobokfest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOwner(User owner);

    @Query("SELECT DISTINCT e.genre FROM Event e WHERE e.genre IS NOT NULL ORDER BY e.genre")
    List<String> findDistinctGenres();

    @Query("SELECT DISTINCT e.venue.city FROM Event e WHERE e.venue.city IS NOT NULL ORDER BY e.venue.city")
    List<String> findDistinctCities();
}