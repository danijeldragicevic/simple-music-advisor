package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.Album;
import advisor.models.OutputPage;
import advisor.repositories.IExternalApiRepository;
import advisor.repositories.impl.ExternalApiRepositoryImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class OutputPageServiceImpl {
    private final IExternalApiRepository repository = new ExternalApiRepositoryImpl();
    
    public OutputPage getAlbums(String accessToken) {
        HttpRequest authRequest = repository.createAuthorizationReq(accessToken, ExternalApiConfig.API_SERVER_PATH + 
                       ExternalApiConfig.NEW_RELEASES_PATH + "?limit=" + ExternalApiConfig.API_PAGE_LIMIT);
        
        String sResponse = repository.getResource(authRequest);
        JsonObject jResponse = JsonParser.parseString(sResponse).getAsJsonObject().getAsJsonObject("albums");
        
        OutputPage outputPage = new OutputPage();
        outputPage.setCurrentPage(1);
        outputPage.setTotalPagesToDisplay(calcTotalPages(jResponse, ExternalApiConfig.API_PAGE_LIMIT));
        outputPage.setNextPageUrl(createNextPageUrl(jResponse));
        outputPage.setPreviousPageUrl(createPreviousPageUrl(jResponse));
        outputPage.setItems(createItems(jResponse));
        
        return outputPage;
    }

    public OutputPage showNextPage(String accessToken, String nextPage, int currentPage) {
        HttpRequest authRequest = repository.createAuthorizationReq(accessToken, nextPage);

        String sResponse = repository.getResource(authRequest);
        JsonObject jResponse = JsonParser.parseString(sResponse).getAsJsonObject().getAsJsonObject("albums");

        OutputPage outputPage = new OutputPage();
        outputPage.setCurrentPage(currentPage + 1);
        outputPage.setTotalPagesToDisplay(calcTotalPages(jResponse, ExternalApiConfig.API_PAGE_LIMIT));
        outputPage.setNextPageUrl(createNextPageUrl(jResponse));
        outputPage.setPreviousPageUrl(createPreviousPageUrl(jResponse));
        outputPage.setItems(createItems(jResponse));

        return outputPage;
    }
    
    public OutputPage showPreviousPage(String accessToken, String previousPage, int currentPage) {
        HttpRequest authRequest = repository.createAuthorizationReq(accessToken, previousPage);

        String sResponse = repository.getResource(authRequest);
        JsonObject jResponse = JsonParser.parseString(sResponse).getAsJsonObject().getAsJsonObject("albums");

        OutputPage outputPage = new OutputPage();
        outputPage.setCurrentPage(currentPage - 1); 
        outputPage.setTotalPagesToDisplay(calcTotalPages(jResponse, ExternalApiConfig.API_PAGE_LIMIT));
        outputPage.setNextPageUrl(createNextPageUrl(jResponse));
        outputPage.setPreviousPageUrl(createPreviousPageUrl(jResponse));
        outputPage.setItems(createItems(jResponse));

        return outputPage;
    }
    
    private int calcTotalPages(JsonObject jResponse, int apiPageLimit) {
        int totalRecords = Integer.parseInt(jResponse.getAsJsonObject().get("total").toString().replaceAll("\"", ""));
        int result = (int) Math.ceil((double) totalRecords / apiPageLimit);
        
        return result;
    }
    
    private String createPreviousPageUrl(JsonObject jResponse) {
        return jResponse.getAsJsonObject().get("previous").toString().replaceAll("\"", "");
    }
    
    private String createNextPageUrl(JsonObject jResponse) {
        return jResponse.getAsJsonObject().get("next").toString().replaceAll("\"", "");
    }
    
    private List<Object> createItems(JsonObject jResponse) {
        List<Object> items = new ArrayList<>();
        for (JsonElement item: jResponse.getAsJsonArray("items")) {

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
