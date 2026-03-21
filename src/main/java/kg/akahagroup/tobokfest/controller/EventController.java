package kg.akahagroup.tobokfest.controller;

import kg.akahagroup.tobokfest.service.EventService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


}
