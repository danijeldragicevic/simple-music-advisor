package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.Album;
import advisor.models.Category;
import advisor.models.OutputPage;
import advisor.models.Playlist;
import advisor.repositories.IExternalApiRepository;
import advisor.repositories.impl.ExternalApiRepositoryImpl;
import advisor.services.IPageService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import static advisor.config.ResourceNames.*;

public class PageServiceImpl implements IPageService {
    private final IExternalApiRepository repository = new ExternalApiRepositoryImpl();
    private String resourceName;
    
    public PageServiceImpl(String resourceName) {
        this.resourceName = resourceName;
    }

    public OutputPage createPage(String accessToken, String resourceUrl, int currentPage) {
        HttpRequest apiAuthRequest = repository.createAuthorizationReq(accessToken, resourceUrl);

        String apiResponse = repository.getResource(apiAuthRequest);
        JsonObject apiResponseJson = JsonParser.parseString(apiResponse).getAsJsonObject().getAsJsonObject(resourceName);

        OutputPage outputPage = new OutputPage();
        outputPage.setCurrentPage(currentPage);
        outputPage.setTotalPagesToDisplay(calcTotalNumOfPages(apiResponseJson, ExternalApiConfig.API_PAGE_LIMIT));
        outputPage.setNextPageUrl(createNextPageUrl(apiResponseJson));
        outputPage.setPreviousPageUrl(createPreviousPageUrl(apiResponseJson));
        outputPage.setResourcePageName(resourceName);

        List<Object> pageItems = new ArrayList<>();
        if (resourceName.equals(ALBUMS.getName())) {
            pageItems = createAlbumItems(apiResponseJson);
        }
        if (resourceName.equals(CATEGORIES.getName())) {
            pageItems = createCategoryItems(apiResponseJson);
        }
        if (resourceName.equals(PLAYLISTS.getName())) {
            pageItems = createPlaylistItems(apiResponseJson);
        }
        outputPage.setPageItems(pageItems);
        
        return outputPage;
    }
    
    @Override
    public int calcTotalNumOfPages(JsonObject apiResponseJson, int apiPageLimit) {
        int totalRecords = Integer.parseInt(apiResponseJson.getAsJsonObject().get("total").toString().replaceAll("\"", ""));
        int result = (int) Math.ceil((double) totalRecords / apiPageLimit);

        return result;
    }

    @Override
    public String createNextPageUrl(JsonObject apiResponseJson) {
        return apiResponseJson.getAsJsonObject().get("next").toString().replaceAll("\"", "");
    }
    
    @Override
    public String createPreviousPageUrl(JsonObject apiResponseJson) {
        return apiResponseJson.getAsJsonObject().get("previous").toString().replaceAll("\"", "");
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
    
    @Override
    public List<Object> createCategoryItems(JsonObject apiResponseJson) {
        List<Object> items = new ArrayList<>();
        for (JsonElement item: apiResponseJson.getAsJsonArray("items")) {
            Category category = new Category();
            category.setName(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            items.add(category);
        }

        return items;
    }
    
    @Override
    public List<Object> createPlaylistItems(JsonObject apiResponseJson) {
        List<Object> items = new ArrayList<>();
        for (JsonElement item: apiResponseJson.getAsJsonArray("items")) {
            Playlist playlist = new Playlist();
            playlist.setName(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            playlist.setExternalUrl(item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").toString().replaceAll("\"", ""));
            items.add(playlist);
        }
        
        return items;
    }
}
