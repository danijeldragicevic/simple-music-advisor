package advisor.services;

import advisor.models.Album;

import java.net.http.HttpRequest;
import java.util.List;

public interface IAlbumService {
    List<Album> getNewReleases(String accessToken);
}
