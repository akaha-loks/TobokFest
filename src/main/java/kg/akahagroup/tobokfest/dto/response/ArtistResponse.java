package kg.akahagroup.tobokfest.dto.response;

import kg.akahagroup.tobokfest.model.Artist;

public record ArtistResponse(

        Long id,
        String name,
        String surname

) {

    public static ArtistResponse from(Artist artist) {
        return new ArtistResponse(
                artist.getId(),
                artist.getName(),
                artist.getSurname()
        );
    }
}