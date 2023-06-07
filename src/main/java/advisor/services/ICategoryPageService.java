package advisor.services;

import advisor.models.OutputPage;
import com.google.gson.JsonObject;

import java.util.List;

public interface ICategoryPageService {
    OutputPage getCategoriesPage(String accessToken);
    OutputPage getCategoriesSubPage(String accessToken, String nextPage, int currentPage);
    List<Object> createCategoryItems(JsonObject apiResponseJson);
}
