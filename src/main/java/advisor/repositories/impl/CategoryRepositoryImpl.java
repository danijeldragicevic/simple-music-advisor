package advisor.repositories.impl;

import advisor.repositories.ICategoryRepository;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CategoryRepositoryImpl implements ICategoryRepository {
    @Override
    public String getAll(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException | IOException e) {
            return "Error response";
        }
    }
}
