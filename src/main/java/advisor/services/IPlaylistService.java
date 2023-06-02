package advisor.services;

import advisor.models.Playlist;

import java.util.List;

public interface IPlaylistService {
    List<Playlist> getPlaylists(String accessToken);
    List<Playlist> getPlaylistsByCategoryName(String accessToken, String cName);
}
