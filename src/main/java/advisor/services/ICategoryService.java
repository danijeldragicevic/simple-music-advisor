package advisor.services;

import advisor.models.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories(String accessToken);
}
