package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.Album;
import advisor.repositories.IAlbumRepository;
import advisor.repositories.IAuthRepository;
import advisor.repositories.impl.AlbumRepositoryImpl;
import advisor.repositories.impl.AuthRepositoryImpl;
import advisor.services.IAlbumService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class AlbumServiceImpl implements IAlbumService {
    private final IAuthRepository authRepository = new AuthRepositoryImpl();
    private final IAlbumRepository albumRepository = new AlbumRepositoryImpl();
    
    @Override
    public List<Album> getNewReleases(String accessToken) {
        HttpRequest request = authRepository.createAuthorizationReq(accessToken, ExternalApiConfig.API_SERVER_PATH + ExternalApiConfig.NEW_RELEASES_PATH);
        String response = albumRepository.getNewReleases(request);

        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonObject albums = jsonResponse.getAsJsonObject("albums");

        List<Album> newReleases = new ArrayList<>();
        for (JsonElement item: albums.getAsJsonArray("items")) {
            
            List<String> artists = new ArrayList<>();
            for (JsonElement artist: item.getAsJsonObject().getAsJsonArray("artists")) {
                artists.add(artist.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            }
            
            Album album = new Album();
            album.setName(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            album.setExternalUrl(item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").toString().replaceAll("\"", ""));
            album.setArtists(artists);

            newReleases.add(album);
        }
        
        return newReleases;
    }
}