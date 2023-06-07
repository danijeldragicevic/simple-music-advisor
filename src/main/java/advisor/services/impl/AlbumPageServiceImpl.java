package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.Album;
import advisor.models.OutputPage;
import advisor.repositories.IExternalApiRepository;
import advisor.repositories.impl.ExternalApiRepositoryImpl;
import advisor.services.IAlbumPageService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class AlbumPageServiceImpl extends PageService implements IAlbumPageService {
    private final IExternalApiRepository repository = new ExternalApiRepositoryImpl();

    public AlbumPageServiceImpl(String resourceName) {
        super(resourceName);
    }

    @Override
    public OutputPage getAlbumsPage(String accessToken) {
        HttpRequest apiAuthRequest = repository.createAuthorizationReq(accessToken, ExternalApiConfig.API_SERVER_PATH + 
                       ExternalApiConfig.NEW_RELEASES_PATH + "?limit=" + ExternalApiConfig.API_PAGE_LIMIT);
        
        String apiResponse = repository.getResource(apiAuthRequest);
        JsonObject apiResponseJson = JsonParser.parseString(apiResponse).getAsJsonObject().getAsJsonObject(resourceName);
        
        OutputPage albumsPage = createOutputPage(apiResponse, resourceName, 1);
        albumsPage.setPageItems(createAlbumItems(apiResponseJson));
        
        return albumsPage;
    }
    
    @Override
    public OutputPage getAlbumsSubPage(String accessToken, String nextPageUrl, int currentPage) {
        HttpRequest apiAuthRequest = repository.createAuthorizationReq(accessToken, nextPageUrl);

        String apiResponse = repository.getResource(apiAuthRequest);
        JsonObject apiResponseJson = JsonParser.parseString(apiResponse).getAsJsonObject().getAsJsonObject(resourceName);

        OutputPage albumsSubPage = createOutputPage(apiResponse, resourceName, currentPage);
        albumsSubPage.setPageItems(createAlbumItems(apiResponseJson));
        
        return albumsSubPage;
    }
    
    @Override
    public List<Object> createAlbumItems(JsonObject apiResponseJson) {
        List<Object> items = new ArrayList<>();
        for (JsonElement item: apiResponseJson.getAsJsonArray("items")) {

            List<String> artists = new ArrayList<>();
            for (JsonElement artist: item.getAsJsonObject().getAsJsonArray("artists")) {
                artists.add(artist.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            }

            Album album = new Album();
            album.setName(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            album.setExternalUrl(item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").toString().replaceAll("\"", ""));
            album.setArtists(artists);

            items.add(album);
        }
        
        return items;
    }
}
