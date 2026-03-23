package kg.akahagroup.tobokfest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.akahagroup.tobokfest.dto.request.UserRequest;
import kg.akahagroup.tobokfest.dto.response.UserResponse;
import kg.akahagroup.tobokfest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "User", description = "CRUD операции для пользователей (админ)")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получить всех админов и организаторов")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен")
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAdminsAndOrganizers() {
        List<UserResponse> users = userService.getAdminsAndOrganizers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить только организаторов")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список организаторов успешно получен")
    })
    @GetMapping("/organizers")
    public ResponseEntity<List<UserResponse>> getOrganizers() {
        List<UserResponse> organizers = userService.getOrganizers();
        return ResponseEntity.ok(organizers);
    }

    @Operation(summary = "Создать нового организатора")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Организатор успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверные данные")
    })
    @PostMapping("/organizer")
    public ResponseEntity<UserResponse> createOrganizer(@RequestBody UserRequest request) {
        try {
            log.info("Creating organizer: {}", request.username());
            UserResponse organizer = userService.createOrganizer(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(organizer);
        } catch (Exception e) {
            log.error("Failed to create organizer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}