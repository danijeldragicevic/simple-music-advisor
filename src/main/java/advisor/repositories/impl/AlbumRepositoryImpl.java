package advisor.repositories.impl;

import advisor.models.Album;
import advisor.repositories.IAlbumRepository;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepositoryImpl implements IAlbumRepository {
    @Override
    public String getNewReleases(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException | IOException e) {
            return "Error response";
        }
    }
}
