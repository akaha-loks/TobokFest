package kg.akahagroup.tobokfest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.akahagroup.tobokfest.dto.request.VenueRequest;
import kg.akahagroup.tobokfest.dto.response.VenueResponse;
import kg.akahagroup.tobokfest.service.VenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/venues")
@Tag(name = "Venue", description = "CRUD операции для площадок (админ)")
public class VenueController {

    private static final Logger log = LoggerFactory.getLogger(VenueController.class);
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    @Operation(summary = "Создание площадки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Площадка успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка данных запроса")
    })
    public ResponseEntity<VenueResponse> create(@RequestBody VenueRequest request) {
        try {
            VenueResponse venue = venueService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(venue);
        } catch (Exception e) {
            log.error("Failed to create venue: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(summary = "Получить все площадки")
    public ResponseEntity<List<VenueResponse>> getAll() {
        List<VenueResponse> venues = venueService.getAll();
        return ResponseEntity.ok(venues);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить площадку по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Площадка найдена"),
            @ApiResponse(responseCode = "404", description = "Площадка не найдена")
    })
    public ResponseEntity<VenueResponse> getById(@PathVariable Long id) {
        try {
            VenueResponse venue = venueService.getById(id);
            return ResponseEntity.ok(venue);
        } catch (Exception e) {
            log.error("Venue not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить площадку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Площадка успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка данных запроса"),
            @ApiResponse(responseCode = "404", description = "Площадка не найдена")
    })
    public ResponseEntity<VenueResponse> update(@PathVariable Long id,
                                                @RequestBody VenueRequest request) {
        try {
            VenueResponse venue = venueService.update(id, request);
            return ResponseEntity.ok(venue);
        } catch (Exception e) {
            log.error("Failed to update venue {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить площадку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Площадка успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Площадка не найдена")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            venueService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Failed to delete venue {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}