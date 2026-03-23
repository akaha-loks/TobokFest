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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/events")
@Tag(name = "Event", description = "CRUD операции для событий (админ)")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Получить все события")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "События успешно получены"),
            @ApiResponse(responseCode = "404", description = "События не найдены")
    })
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Создать новое событие")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Событие успешно создано"),
            @ApiResponse(responseCode = "400", description = "Неверные данные события"),
            @ApiResponse(responseCode = "404", description = "Пользователь, площадка или артисты не найдены")
    })
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventRequest request) {
        try {
            EventResponse event = eventService.createEvent(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(event);
        } catch (Exception e) {
            log.error("Failed to create event: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Обновить событие")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Событие успешно обновлено"),
            @ApiResponse(responseCode = "400", description = "Неверные данные события"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к событию"),
            @ApiResponse(responseCode = "404", description = "Событие не найдено")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @RequestBody EventRequest request) {
        try {
            EventResponse event = eventService.updateEvent(id, request);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            log.error("Failed to update event {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Удалить событие")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Событие успешно удалено"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к событию"),
            @ApiResponse(responseCode = "404", description = "Событие не найдено")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Failed to delete event {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Поиск событий с фильтрацией")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "События успешно получены")
    })
    @GetMapping("/search")
    public ResponseEntity<List<EventResponse>> getEvents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end
    ) {
        List<EventResponse> events = eventService.getEvents(title, genre, city, start, end);
        return ResponseEntity.ok(events);
    }
}