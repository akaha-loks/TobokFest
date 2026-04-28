package kg.akahagroup.tobokfest.dto.request;

public record AuthRequest(
        String username,
        String password
) {}