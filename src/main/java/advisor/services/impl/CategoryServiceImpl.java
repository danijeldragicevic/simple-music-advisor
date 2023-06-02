package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.Category;
import advisor.repositories.IExternalApiRepository;
import advisor.repositories.impl.ExternalApiRepositoryImpl;
import advisor.services.ICategoryService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    private final IExternalApiRepository repository = new ExternalApiRepositoryImpl();
    
    @Override
    public List<Category> getAll(String accessToken) {
        HttpRequest request = repository.createAuthorizationReq(accessToken, ExternalApiConfig.API_SERVER_PATH + ExternalApiConfig.CATEGORIES_PATH);
        String response = repository.getAllCategories(request);

        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonObject jsonCategories = jsonResponse.getAsJsonObject("categories");
        
        List<Category> categories = new ArrayList<>();
        for (JsonElement item: jsonCategories.getAsJsonArray("items")) {
            Category category = new Category();
            category.setName(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            categories.add(category);
        }

        return categories;
    }
}
