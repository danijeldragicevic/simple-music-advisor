package advisor.repositories;

import advisor.models.Playlist;

import java.util.List;

public interface IPlaylistRepository {
    List<Playlist> getAll();
    List<Playlist> getAllByCategoryName(String categoryName);
}
