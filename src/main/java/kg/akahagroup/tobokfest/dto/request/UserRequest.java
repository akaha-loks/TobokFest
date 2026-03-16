package kg.akahagroup.tobokfest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank @Email
        String email,

        @NotBlank @Size(max = 50)
        String username,

        @NotBlank @Size(min = 6)
        String password
) {
}
