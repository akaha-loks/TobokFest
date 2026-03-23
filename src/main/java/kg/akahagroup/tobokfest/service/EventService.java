package kg.akahagroup.tobokfest.service;

import kg.akahagroup.tobokfest.dto.request.EventRequest;
import kg.akahagroup.tobokfest.dto.response.EventResponse;
import kg.akahagroup.tobokfest.enums.UserRoles;
import kg.akahagroup.tobokfest.exception.ResourceNotFoundException;
import kg.akahagroup.tobokfest.model.*;
import kg.akahagroup.tobokfest.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

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

    // ==================== ADMIN METHODS ====================

    @Transactional
    public EventResponse createEventByAdmin(EventRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Admin {} creating event", username);

        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Проверяем, что пользователь имеет роль ADMIN
        if (owner.getRole() != UserRoles.ADMIN) {
            throw new AccessDeniedException("Only ADMIN can create events through admin endpoint");
        }

        return createEventInternal(request, owner);
    }

    @Transactional
    public EventResponse updateEventByAdmin(Long id, EventRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Admin {} updating event {}", username, id);

        User admin = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (admin.getRole() != UserRoles.ADMIN) {
            throw new AccessDeniedException("Only ADMIN can update any event");
        }

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        return updateEventInternal(event, request);
    }

    @Transactional
    public void deleteEventByAdmin(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Admin {} deleting event {}", username, id);

        User admin = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (admin.getRole() != UserRoles.ADMIN) {
            throw new AccessDeniedException("Only ADMIN can delete any event");
        }

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        eventRepository.delete(event);
        log.info("Event {} deleted by admin {}", id, username);
    }

    // ==================== ORGANIZER METHODS ====================

    @Transactional
    public EventResponse createEventByOrganizer(EventRequest request, String username) {
        log.info("Organizer {} creating event", username);

        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Проверяем, что пользователь имеет роль ORGANIZER
        if (owner.getRole() != UserRoles.ORGANIZER) {
            throw new AccessDeniedException("Only ORGANIZER can create events through organizer endpoint");
        }

        return createEventInternal(request, owner);
    }

    @Transactional
    public EventResponse updateEventByOrganizer(Long id, EventRequest request, String username) {
        log.info("Organizer {} updating event {}", username, id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        // Проверяем, что событие принадлежит этому организатору
        if (!event.getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only update your own events");
        }

        return updateEventInternal(event, request);
    }

    @Transactional
    public void deleteEventByOrganizer(Long id, String username) {
        log.info("Organizer {} deleting event {}", username, id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        // Проверяем, что событие принадлежит этому организатору
        if (!event.getOwner().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only delete your own events");
        }

        eventRepository.delete(event);
        log.info("Event {} deleted by organizer {}", id, username);
    }

    public List<EventResponse> getEventsByOrganizer(String username) {
        log.info("Getting events for organizer: {}", username);

        User organizer = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return eventRepository.findByOwner(organizer)
                .stream()
                .map(EventResponse::from)
                .toList();
    }

    // ==================== PUBLIC METHODS ====================

    public List<EventResponse> getAllEvents() {
        log.info("Getting all events");
        return eventRepository.findAll()
                .stream()
                .map(EventResponse::from)
                .toList();
    }

    public EventResponse getEventById(Long id) {
        log.info("Getting event by id: {}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        return EventResponse.from(event);
    }

    public List<EventResponse> searchEvents(String title, String genre, String city,
                                            LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Searching events - title: {}, genre: {}, city: {}, start: {}, end: {}",
                title, genre, city, startDate, endDate);

        return eventRepository.findEvents(title, genre, city, startDate, endDate)
                .stream()
                .map(EventResponse::from)
                .toList();
    }

    // ==================== INTERNAL METHODS ====================

    private EventResponse createEventInternal(EventRequest request, User owner) {
        Venue venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + request.venueId()));

        List<Artist> artists = artistRepository.findAllById(request.artistIds());
        if (artists.isEmpty()) {
            throw new ResourceNotFoundException("No artists found for given IDs: " + request.artistIds());
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

        Event savedEvent = eventRepository.save(event);
        log.info("Event created successfully with id: {}", savedEvent.getId());

        return EventResponse.from(savedEvent);
    }

    private EventResponse updateEventInternal(Event event, EventRequest request) {
        Venue venue = venueRepository.findById(request.venueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + request.venueId()));

        List<Artist> artists = artistRepository.findAllById(request.artistIds());
        if (artists.isEmpty()) {
            throw new ResourceNotFoundException("No artists found for given IDs: " + request.artistIds());
        }

        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setDate(request.date());
        event.setGenre(request.genre());
        event.setPrice(request.price());
        event.setVenue(venue);
        event.setArtists(artists);

        Event updatedEvent = eventRepository.save(event);
        log.info("Event updated successfully with id: {}", updatedEvent.getId());

        return EventResponse.from(updatedEvent);
    }
}