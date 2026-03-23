package kg.akahagroup.tobokfest.controller;

import kg.akahagroup.tobokfest.dto.request.EventRequest;
import kg.akahagroup.tobokfest.dto.response.EventResponse;
import kg.akahagroup.tobokfest.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public EventResponse createEvent(@RequestBody EventRequest request) {
        return eventService.createEvent(request);
    }

    @PutMapping("/{id}")
    public EventResponse updateEvent(
            @PathVariable Long id,
            @RequestBody EventRequest request) {

        return eventService.updateEvent(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping
    public List<EventResponse> getEvents(

            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String city,

            @RequestParam(required = false)
            LocalDateTime start,

            @RequestParam(required = false)
            LocalDateTime end
    ) {
        return eventService.getEvents(title, genre, city, start, end);
    }
}