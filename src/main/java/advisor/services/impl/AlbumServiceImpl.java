package advisor.services.impl;

import advisor.models.Album;
import advisor.services.IAlbumService;

import java.util.List;

public class AlbumServiceImpl implements IAlbumService {
    private List<Album> albums = List.of(
            new Album("Mountains", List.of("Sia", "Diplo", "Labrinth")),
            new Album("Runaway", List.of("Lil Peep")),
            new Album("The Greatest Show", List.of("Panic! At The Disco")),
            new Album("All Out Life", List.of("Slipknot"))
    );
    
    @Override
    public List<Album> getAll() {
        return albums;
    }
}
