package kg.akahagroup.tobokfest.dto.response;

import kg.akahagroup.tobokfest.model.User;

public record UserResponse(
    Long id,
    String email,
    String username,
    String role
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getUsername(), user.getRole().name());
    }
}
