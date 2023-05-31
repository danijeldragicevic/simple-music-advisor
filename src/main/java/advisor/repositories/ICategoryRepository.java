package advisor.repositories;

import advisor.models.Category;

import java.util.List;

public interface ICategoryRepository {
    List<Category> getAll();
}
