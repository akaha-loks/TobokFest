package kg.akahagroup.tobokfest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank
        @Size(max = 50)
        String username,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6)
        String password
) {}