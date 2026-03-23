package kg.akahagroup.tobokfest.controller.publicControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.akahagroup.tobokfest.dto.response.ArtistResponse;
import kg.akahagroup.tobokfest.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
@Tag(name = "Public Artists", description = "Публичный просмотр артистов (доступен всем)")
public class PublicArtistController {

    private final ArtistService artistService;

    public PublicArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Operation(summary = "Получить всех артистов")
    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        List<ArtistResponse> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @Operation(summary = "Получить артиста по ID")
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getArtist(@PathVariable Long id) {
        ArtistResponse artist = artistService.getArtist(id);
        return ResponseEntity.ok(artist);
    }

    @Operation(summary = "Поиск артистов по имени или фамилии")
    @GetMapping("/search")
    public ResponseEntity<List<ArtistResponse>> searchArtists(@RequestParam String query) {
        List<ArtistResponse> artists = artistService.searchArtists(query);
        return ResponseEntity.ok(artists);
    }
}