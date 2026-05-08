package kg.akahagroup.tobokfest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @Size(max = 50)
        String username,

        @Email
        String email,

        @Size(min = 6)
        String password  // опциональный — можно не передавать
) {}