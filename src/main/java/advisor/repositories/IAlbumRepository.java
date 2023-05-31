package advisor.repositories;

import advisor.models.Album;

import java.net.http.HttpRequest;
import java.util.List;

public interface IAlbumRepository {
    String getNewReleases(HttpRequest request);
}
