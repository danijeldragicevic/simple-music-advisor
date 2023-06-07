package advisor.services;

import advisor.models.OutputPage;
import com.google.gson.JsonObject;

import java.util.List;

public interface IPageService {
    OutputPage createPage(String accessToken, String resourceUrl, int currentPage);
    int calcTotalNumOfPages(JsonObject jResponse, int apiPageLimit);
    String createNextPageUrl(JsonObject jResponse);
    String createPreviousPageUrl(JsonObject jResponse);
    List<Object> createAlbumItems(JsonObject apiResponseJson);
    List<Object> createCategoryItems(JsonObject apiResponseJson);
}
