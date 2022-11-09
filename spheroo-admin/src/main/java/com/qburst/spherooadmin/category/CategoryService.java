package com.qburst.spherooadmin.category;

import org.springframework.data.domain.Page;

public interface CategoryService {

    void saveCategory(Category category);

    Category getCategory(Long id);

    void updateCategoryById(Long categoryId, Category category);

    void deleteCategory(Long id);

    Page<Category> getAllCategoriesPaged(int pageNo, int noOfElements);

    void updateCategoryIconById(Long categoryId, String categoryIconPath);
}
