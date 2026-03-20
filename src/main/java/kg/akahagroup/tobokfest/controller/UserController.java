package kg.akahagroup.tobokfest.controller;

import kg.akahagroup.tobokfest.dto.request.UserRequest;
import kg.akahagroup.tobokfest.enums.UserRoles;
import kg.akahagroup.tobokfest.model.User;
import kg.akahagroup.tobokfest.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping
    public List<User> getOrganizers() {
        return userService.getAllUsers();
    }

    @PostMapping("/organizer")
    public User createOrganizer(@RequestBody UserRequest request) {
        return userService.createOrganizer(
                request.username(),
                request.email(),
                request.password(),
                request.adminId()
        );
    }
}
