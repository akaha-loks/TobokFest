package kg.akahagroup.tobokfest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArtistRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 100)
        String surname

) {}