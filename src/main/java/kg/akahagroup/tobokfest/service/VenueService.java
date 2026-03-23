package kg.akahagroup.tobokfest.service;

import kg.akahagroup.tobokfest.dto.request.VenueRequest;
import kg.akahagroup.tobokfest.dto.response.VenueResponse;
import kg.akahagroup.tobokfest.exception.ResourceNotFoundException;
import kg.akahagroup.tobokfest.model.Venue;
import kg.akahagroup.tobokfest.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public VenueResponse create(VenueRequest request) {
        Venue venue = new Venue(
                request.name(),
                request.oblast(),
                request.city(),
                request.address(),
                request.capacity()
        );

        venueRepository.save(venue);
        return mapToResponse(venue);
    }

    public List<VenueResponse> getAll() {
        return venueRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public VenueResponse getById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue with id " + id + " not found"));

        return mapToResponse(venue);
    }

    public VenueResponse update(Long id, VenueRequest request) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue with id " + id + " not found"));

        venue.setName(request.name());
        venue.setOblast(request.oblast());
        venue.setCity(request.city());
        venue.setAddress(request.address());
        venue.setCapacity(request.capacity());

        venueRepository.save(venue);
        return mapToResponse(venue);
    }

    public void delete(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue with id " + id + " not found"));
        venueRepository.delete(venue);
    }

    private VenueResponse mapToResponse(Venue venue) {
        return new VenueResponse(
                venue.getId(),
                venue.getName(),
                venue.getOblast(),
                venue.getCity(),
                venue.getAddress(),
                venue.getCapacity()
        );
    }
}