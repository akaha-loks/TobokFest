package kg.akahagroup.tobokfest.service;

import kg.akahagroup.tobokfest.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public  EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


}
