package advisor.repositories;

import advisor.models.Playlist;

import java.net.http.HttpRequest;
import java.util.List;

public interface IPlaylistRepository {
    String getFeaturedPlaylists(HttpRequest request);
    List<Playlist> getAllByCategoryName(String categoryName);
}
