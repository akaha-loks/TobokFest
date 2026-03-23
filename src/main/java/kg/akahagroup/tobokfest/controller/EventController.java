package kg.akahagroup.tobokfest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.akahagroup.tobokfest.dto.request.EventRequest;
import kg.akahagroup.tobokfest.dto.response.EventResponse;
import kg.akahagroup.tobokfest.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizer/events")
@Tag(name = "Organizer Events", description = "Управление событиями (только ORGANIZER)")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Создать новое событие")
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            log.info("Creating event by organizer: {}", username);
            EventResponse event = eventService.createEventByOrganizer(request, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(event);
        } catch (Exception e) {
            log.error("Failed to create event: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Получить все свои события")
    @GetMapping
    public ResponseEntity<List<EventResponse>> getMyEvents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<EventResponse> events = eventService.getEventsByOrganizer(username);
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Обновить свое событие")
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @RequestBody EventRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            EventResponse event = eventService.updateEventByOrganizer(id, request, username);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            log.error("Failed to update event {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Удалить свое событие")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            eventService.deleteEventByOrganizer(id, username);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Failed to delete event {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}