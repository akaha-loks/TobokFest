package kg.akahagroup.tobokfest.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record EventRequest(
        @NotBlank @Size(max = 255)
        String title,

        @NotBlank
        String description,

        @Future(message = "Event date must be in the future")
        LocalDateTime date,

        @NotBlank
        String genre,

        @PositiveOrZero
        int price,

        @NotNull
        Long venueId,

        @NotNull
        List<Long> artistIds
) {
}
