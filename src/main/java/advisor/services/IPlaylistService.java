package advisor.services;

import advisor.models.Playlist;

import java.util.List;

public interface IPlaylistService {
    List<Playlist> getAll();
    List<Playlist> getAllByCategoryName(String categoryName);
}
