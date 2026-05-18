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
        String venueName,
        String venueCity,
        String venueAddress,
        String venueOblast,
        int venueCapacity,

        List<Long> artistIds,
        List<String> artistNames,

        Long ownerId
) {

    public static EventResponse from(Event event) {

        List<Long> artistIds = event.getArtists()
                .stream()
                .map(Artist::getId)
                .toList();

        List<String> artistNames = event.getArtists()
                .stream()
                .map(a -> a.getName() + " " + a.getSurname())
                .toList();

        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getGenre(),
                event.getPrice(),

                event.getVenue().getId(),
                event.getVenue().getName(),
                event.getVenue().getCity(),
                event.getVenue().getAddress(),
                event.getVenue().getOblast().getLabel(),
                event.getVenue().getCapacity(),

                artistIds,
                artistNames,

                event.getOwner().getId()
        );
    }
}