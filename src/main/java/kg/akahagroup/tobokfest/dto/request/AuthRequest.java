package kg.akahagroup.tobokfest.dto.request;

public record AuthRequest(
        String email,
        String password
) {}