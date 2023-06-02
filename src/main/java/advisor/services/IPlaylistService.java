package advisor.services;

import advisor.models.Playlist;

import java.util.List;

public interface IPlaylistService {
    List<Playlist> getAll(String accessToken);
    List<Playlist> getByCategoryName(String accessToken, String cName);
}
