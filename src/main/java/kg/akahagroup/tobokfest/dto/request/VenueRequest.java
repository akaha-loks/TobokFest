package kg.akahagroup.tobokfest.dto.request;

import kg.akahagroup.tobokfest.enums.Oblasts;

public record VenueRequest(
        String name,
        Oblasts oblast,
        String city,
        String address,
        int capacity
) {
}