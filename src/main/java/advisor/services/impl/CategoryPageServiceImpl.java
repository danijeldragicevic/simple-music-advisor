package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.Category;
import advisor.models.OutputPage;
import advisor.repositories.IExternalApiRepository;
import advisor.repositories.impl.ExternalApiRepositoryImpl;
import advisor.services.ICategoryPageService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class CategoryPageServiceImpl extends PageService implements ICategoryPageService {
    private final IExternalApiRepository repository = new ExternalApiRepositoryImpl();

    public CategoryPageServiceImpl(String resourceName) {
        super(resourceName);
    }

    @Override
    public OutputPage getCategoriesPage(String accessToken) {
        HttpRequest apiAuthRequest = repository.createAuthorizationReq(accessToken, ExternalApiConfig.API_SERVER_PATH + 
                ExternalApiConfig.CATEGORIES_PATH + "?limit=" + ExternalApiConfig.API_PAGE_LIMIT);
        
        String apiResponse = repository.getResource(apiAuthRequest);
        JsonObject apiResponseJson = JsonParser.parseString(apiResponse).getAsJsonObject().getAsJsonObject(resourceName);

        OutputPage categoriesPage = createOutputPage(apiResponse, resourceName, 1);
        categoriesPage.setPageItems(createCategoryItems(apiResponseJson));
        
        return categoriesPage;
    }

    @Override
    public OutputPage getCategoriesSubPage(String accessToken, String nextPageUrl, int currentPage) {
        HttpRequest apiAuthRequest = repository.createAuthorizationReq(accessToken, nextPageUrl);
        
        String apiResponse = repository.getResource(apiAuthRequest);
        JsonObject apiResponseJson = JsonParser.parseString(apiResponse).getAsJsonObject().getAsJsonObject(resourceName);
        
        OutputPage categoriesSubPage = createOutputPage(apiResponse, resourceName, currentPage);
        categoriesSubPage.setPageItems(createCategoryItems(apiResponseJson));
        
        return categoriesSubPage;
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
}
