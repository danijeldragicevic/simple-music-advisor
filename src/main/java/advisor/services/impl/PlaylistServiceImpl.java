package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.Playlist;
import advisor.repositories.IExternalApiRepository;
import advisor.repositories.impl.ExternalApiRepositoryImpl;
import advisor.services.IPlaylistService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class PlaylistServiceImpl implements IPlaylistService {
    private final IExternalApiRepository repository = new ExternalApiRepositoryImpl();
    
    @Override
    public List<Playlist> getPlaylists(String accessToken) {
        HttpRequest request = repository.createAuthorizationReq(
                accessToken, ExternalApiConfig.API_SERVER_PATH + ExternalApiConfig.FEATURED_PLAYLISTS_PATH);
        
        return getPlaylists(request);
    }

    @Override
    public List<Playlist> getPlaylistsByCategoryName(String accessToken, String cName) {
        HttpRequest request = repository.createAuthorizationReq(
                accessToken, ExternalApiConfig.API_SERVER_PATH + ExternalApiConfig.CATEGORIES_PATH + "/" + cName + "/playlists");
        
        return getPlaylists(request);
    }

    private List<Playlist> getPlaylists(HttpRequest request) {
        String response = repository.getResource(request);

        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonObject jsonPlaylists = jsonResponse.getAsJsonObject("playlists");

        List<Playlist> playlists = new ArrayList<>();
        for (JsonElement item: jsonPlaylists.getAsJsonArray("items")) {
            Playlist playlist = new Playlist();
            playlist.setName(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            playlist.setExternalUrl(item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").toString().replaceAll("\"", ""));
            playlists.add(playlist);
        }

        return playlists;
    }
}
