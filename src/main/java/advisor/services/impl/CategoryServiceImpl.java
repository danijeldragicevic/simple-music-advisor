package advisor.services.impl;

import advisor.models.Category;
import advisor.services.ICategoryService;

import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    private List<Category> categories = List.of(
            Category.TOP_LISTS,
            Category.POP,
            Category.MOOD,
            Category.LATIN
    );
    
    @Override
    public List<Category> getAll() {
        return categories;
    }
}
