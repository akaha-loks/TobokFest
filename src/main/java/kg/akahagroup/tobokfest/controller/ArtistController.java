package kg.akahagroup.tobokfest.controller;

import kg.akahagroup.tobokfest.dto.request.ArtistRequest;
import kg.akahagroup.tobokfest.dto.response.ArtistResponse;
import kg.akahagroup.tobokfest.service.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<ArtistResponse> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    public ArtistResponse getArtist(@PathVariable Long id) {
        return artistService.getArtist(id);
    }

    @PostMapping
    public ArtistResponse createArtist(@RequestBody ArtistRequest request) {
        return artistService.createArtist(request);
    }

    @PutMapping("/{id}")
    public ArtistResponse updateArtist(
            @PathVariable Long id,
            @RequestBody ArtistRequest request) {

        return artistService.updateArtist(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
    }

    @GetMapping("/search")
    public List<ArtistResponse> searchArtists(@RequestParam String query) {
        return artistService.searchArtists(query);
    }
}