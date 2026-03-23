package kg.akahagroup.tobokfest.controller;

import kg.akahagroup.tobokfest.dto.request.UserRequest;
import kg.akahagroup.tobokfest.dto.response.UserResponse;
import kg.akahagroup.tobokfest.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAdminsAndOrganizers() {
        return userService.getAdminsAndOrganizers();
    }

    @GetMapping("/organizers")
    public List<UserResponse> getOrganizers() {
        return userService.getOrganizers();
    }

    @PostMapping("/organizer")
    public UserResponse createOrganizer(@RequestBody UserRequest request) {
        return userService.createOrganizer(request);
    }
}