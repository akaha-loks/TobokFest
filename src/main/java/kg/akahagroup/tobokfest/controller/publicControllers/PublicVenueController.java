package kg.akahagroup.tobokfest.controller.publicControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.akahagroup.tobokfest.dto.response.VenueResponse;
import kg.akahagroup.tobokfest.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
@Tag(name = "Public Venues", description = "Публичный просмотр площадок (доступен всем)")
public class PublicVenueController {

    private final VenueService venueService;

    public PublicVenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @Operation(summary = "Получить все площадки")
    @GetMapping
    public ResponseEntity<List<VenueResponse>> getAllVenues() {
        List<VenueResponse> venues = venueService.getAll();
        return ResponseEntity.ok(venues);
    }

    @Operation(summary = "Получить площадку по ID")
    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> getVenueById(@PathVariable Long id) {
        VenueResponse venue = venueService.getById(id);
        return ResponseEntity.ok(venue);
    }
}