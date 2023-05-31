package advisor.repositories.impl;

import advisor.models.Category;
import advisor.repositories.ICategoryRepository;

import java.util.List;

public class CategoryRepositoryImpl implements ICategoryRepository {
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
