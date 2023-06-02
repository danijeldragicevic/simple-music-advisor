package advisor.repositories;

import java.net.http.HttpRequest;

public interface IPlaylistRepository {
    String getAll(HttpRequest request);
    String getByCategoryName(HttpRequest request);
}
