package kg.akahagroup.tobokfest.dto.response;

import kg.akahagroup.tobokfest.enums.Oblasts;

public record VenueResponse(
        Long id,
        String name,
        Oblasts oblast,
        String city,
        String address,
        int capacity
) {
}