package advisor.services.impl;

import advisor.config.ExternalApiConfig;
import advisor.models.Category;
import advisor.repositories.IAuthRepository;
import advisor.repositories.ICategoryRepository;
import advisor.repositories.impl.AuthRepositoryImpl;
import advisor.repositories.impl.CategoryRepositoryImpl;
import advisor.services.ICategoryService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    private final IAuthRepository authRepository = new AuthRepositoryImpl();
    private final ICategoryRepository categoryRepository = new CategoryRepositoryImpl();
    
    @Override
    public List<Category> getAll(String accessToken) {
        HttpRequest request = authRepository.createAuthorizationReq(accessToken, ExternalApiConfig.API_SERVER_PATH + ExternalApiConfig.CATEGORIES_PATH);
        String response = categoryRepository.getAll(request);

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
