package kg.akahagroup.tobokfest.controller;

import kg.akahagroup.tobokfest.dto.request.VenueRequest;
import kg.akahagroup.tobokfest.dto.response.VenueResponse;
import kg.akahagroup.tobokfest.service.VenueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    public VenueResponse create(@RequestBody VenueRequest request) {
        return venueService.create(request);
    }

    @GetMapping
    public List<VenueResponse> getAll() {
        return venueService.getAll();
    }

    @GetMapping("/{id}")
    public VenueResponse getById(@PathVariable Long id) {
        return venueService.getById(id);
    }

    @PutMapping("/{id}")
    public VenueResponse update(@PathVariable Long id,
                                @RequestBody VenueRequest request) {
        return venueService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        venueService.delete(id);
    }
}