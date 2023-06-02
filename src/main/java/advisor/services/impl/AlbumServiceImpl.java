package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.Album;
import advisor.repositories.IExternalApiRepository;
import advisor.repositories.impl.ExternalApiRepositoryImpl;
import advisor.services.IAlbumService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class AlbumServiceImpl implements IAlbumService {
    private final IExternalApiRepository repository = new ExternalApiRepositoryImpl();
    
    @Override
    public List<Album> getNewReleases(String accessToken) {
        HttpRequest request = repository.createAuthorizationReq(accessToken, ExternalApiConfig.API_SERVER_PATH + ExternalApiConfig.NEW_RELEASES_PATH);
        String response = repository.getAllAlbums(request);

        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonObject jsonAlbums = jsonResponse.getAsJsonObject("albums");

        List<Album> albums = new ArrayList<>();
        for (JsonElement item: jsonAlbums.getAsJsonArray("items")) {
            
            List<String> artists = new ArrayList<>();
            for (JsonElement artist: item.getAsJsonObject().getAsJsonArray("artists")) {
                artists.add(artist.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            }
            
            Album album = new Album();
            album.setName(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            album.setExternalUrl(item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").toString().replaceAll("\"", ""));
            album.setArtists(artists);

            albums.add(album);
        }
        
        return albums;
    }
}
