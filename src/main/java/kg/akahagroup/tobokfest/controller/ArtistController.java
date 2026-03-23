package kg.akahagroup.tobokfest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.akahagroup.tobokfest.dto.request.ArtistRequest;
import kg.akahagroup.tobokfest.dto.response.ArtistResponse;
import kg.akahagroup.tobokfest.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/artists")
@Tag(name = "Artist", description = "CRUD операции для артистов (админ)")
public class ArtistController {

    private static final Logger log = LoggerFactory.getLogger(ArtistController.class);
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Operation(summary = "Получить всех артистов")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Артисты успешно получены")
    })
    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        List<ArtistResponse> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @Operation(summary = "Получить артиста по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Артист найден"),
            @ApiResponse(responseCode = "404", description = "Артист не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getArtist(@PathVariable Long id) {
        try {
            ArtistResponse artist = artistService.getArtist(id);
            return ResponseEntity.ok(artist);
        } catch (Exception e) {
            log.error("Artist not found with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Создать артиста", description = "Добавляет нового артиста")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Артист успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    @PostMapping
    public ResponseEntity<ArtistResponse> createArtist(@RequestBody ArtistRequest request) {
        try {
            ArtistResponse artist = artistService.createArtist(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(artist);
        } catch (Exception e) {
            log.error("Failed to create artist: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Обновить артиста")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Артист успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Артист не найден")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponse> updateArtist(
            @PathVariable Long id,
            @RequestBody ArtistRequest request) {
        try {
            ArtistResponse artist = artistService.updateArtist(id, request);
            return ResponseEntity.ok(artist);
        } catch (Exception e) {
            log.error("Failed to update artist {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Удалить артиста")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Артист успешно удален"),
            @ApiResponse(responseCode = "404", description = "Артист не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        try {
            artistService.deleteArtist(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Failed to delete artist {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Поиск артистов по имени или фамилии")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Артисты успешно найдены")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ArtistResponse>> searchArtists(@RequestParam String query) {
        List<ArtistResponse> artists = artistService.searchArtists(query);
        return ResponseEntity.ok(artists);
    }
}