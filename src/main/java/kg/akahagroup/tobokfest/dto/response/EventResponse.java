package kg.akahagroup.tobokfest.dto.response;

import kg.akahagroup.tobokfest.model.Artist;
import kg.akahagroup.tobokfest.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public record EventResponse(
        Long id,
        String title,
        String description,
        LocalDateTime date,
        String genre,
        int price,
        Long venueId,
        List<Long> artistIds,
        Long ownerId
) {
    public static EventResponse from(Event event) {
        List<Long> artistIds = event.getArtists()
                .stream()
                .map(Artist::getId)
                .toList();

        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getGenre(),
                event.getPrice(),
                event.getVenue().getId(),
                artistIds,
                event.getOwner().getId()
        );
    }
}
