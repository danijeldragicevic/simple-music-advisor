package advisor.services;

import advisor.models.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getCategories(String accessToken);
}
