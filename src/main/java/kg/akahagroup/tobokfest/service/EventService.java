package kg.akahagroup.tobokfest.service;

import kg.akahagroup.tobokfest.dto.request.EventRequest;
import kg.akahagroup.tobokfest.dto.response.EventResponse;
import kg.akahagroup.tobokfest.exception.ResourceNotFoundException;
import kg.akahagroup.tobokfest.model.*;
import kg.akahagroup.tobokfest.repository.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final ArtistRepository artistRepository;

    public EventService(EventRepository eventRepository,
                        UserRepository userRepository,
                        VenueRepository venueRepository,
                        ArtistRepository artistRepository) {

        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
        this.artistRepository = artistRepository;
    }

    public EventResponse createEvent(EventRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Venue venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

        List<Artist> artists = artistRepository.findAllById(request.artistIds());
        if (artists.isEmpty()) {
            throw new ResourceNotFoundException("No artists found for given IDs");
        }

        Event event = new Event();
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setDate(request.date());
        event.setGenre(request.genre());
        event.setPrice(request.price());
        event.setVenue(venue);
        event.setArtists(artists);
        event.setOwner(owner);

        return EventResponse.from(eventRepository.save(event));
    }

    public EventResponse updateEvent(Long id, EventRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!event.getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not the owner of this event");
        }

        Venue venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

        List<Artist> artists = artistRepository.findAllById(request.artistIds());
        if (artists.isEmpty()) {
            throw new ResourceNotFoundException("No artists found for given IDs");
        }

        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setDate(request.date());
        event.setGenre(request.genre());
        event.setPrice(request.price());
        event.setVenue(venue);
        event.setArtists(artists);

        return EventResponse.from(eventRepository.save(event));
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!event.getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not the owner of this event");
        }

        eventRepository.delete(event);
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(EventResponse::from)
                .toList();
    }

    public List<EventResponse> getEvents(String title, String genre, String city,
                                         LocalDateTime start, LocalDateTime end) {
        return eventRepository
                .findEvents(title, genre, city, start, end)
                .stream()
                .map(EventResponse::from)
                .toList();
    }
}