package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.Playlist;
import advisor.repositories.IAuthRepository;
import advisor.repositories.IPlaylistRepository;
import advisor.repositories.impl.AuthRepositoryImpl;
import advisor.repositories.impl.PlaylistRepositoryImpl;
import advisor.services.IPlaylistService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class PlaylistServiceImpl implements IPlaylistService {
    private final IAuthRepository authRepository = new AuthRepositoryImpl();
    private final IPlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
    
    @Override
    public List<Playlist> getAll(String accessToken) {
        HttpRequest request = authRepository.createAuthorizationReq(
                accessToken, ExternalApiConfig.API_SERVER_PATH + ExternalApiConfig.FEATURED_PLAYLISTS_PATH);
        
        return getPlaylists(request);
    }

    @Override
    public List<Playlist> getByCategoryName(String accessToken, String cName) {
        HttpRequest request = authRepository.createAuthorizationReq(
                accessToken, ExternalApiConfig.API_SERVER_PATH + ExternalApiConfig.CATEGORIES_PATH + "/" + cName + "/playlists");
        
        return getPlaylists(request);
    }

    private List<Playlist> getPlaylists(HttpRequest request) {
        String response = playlistRepository.getAll(request);

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
