package advisor.repositories;

import java.net.http.HttpRequest;

public interface ICategoryRepository {
    String getAll(HttpRequest request);
}
