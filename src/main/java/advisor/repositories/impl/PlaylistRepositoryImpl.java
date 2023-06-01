package advisor.repositories.impl;

import advisor.models.Playlist;
import advisor.repositories.IPlaylistRepository;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PlaylistRepositoryImpl implements IPlaylistRepository {
    @Override
    public String getFeaturedPlaylists(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException | IOException e) {
            return "Error response";
        }
    }

    @Override
    public List<Playlist> getAllByCategoryName(String categoryName) {
        return null;
    }
}
