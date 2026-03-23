package kg.akahagroup.tobokfest.service;

import kg.akahagroup.tobokfest.dto.request.ArtistRequest;
import kg.akahagroup.tobokfest.dto.response.ArtistResponse;
import kg.akahagroup.tobokfest.model.Artist;
import kg.akahagroup.tobokfest.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<ArtistResponse> getAllArtists() {
        return artistRepository.findAll()
                .stream()
                .map(ArtistResponse::from)
                .toList();
    }

    public ArtistResponse getArtist(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        return ArtistResponse.from(artist);
    }

    public ArtistResponse createArtist(ArtistRequest request) {

        Artist artist = new Artist();
        artist.setName(request.name());
        artist.setSurname(request.surname());

        return ArtistResponse.from(artistRepository.save(artist));
    }

    public ArtistResponse updateArtist(Long id, ArtistRequest request) {

        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        artist.setName(request.name());
        artist.setSurname(request.surname());

        return ArtistResponse.from(artistRepository.save(artist));
    }

    public void deleteArtist(Long id) {

        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        artistRepository.delete(artist);
    }

    public List<ArtistResponse> searchArtists(String query) {

        return artistRepository
                .findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(query, query)
                .stream()
                .map(ArtistResponse::from)
                .toList();
    }
}