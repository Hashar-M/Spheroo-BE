package com.qburst.spherooadmin.category;

import org.springframework.data.domain.Page;

/**
 * The service for the Category entity.
 */
public interface CategoryService {

    /**
     * Inserts a new category into the database.
     * @param category The category to be inserted into the database.
     */
    void saveCategory(Category category);

    /**
     * Get a category specified by its id.
     * @param id The category to get from the database.
     */
    Category getCategory(Long id);


    /**
     * Updates an existing category specified by its id.
     * @param categoryId the id of the category to update.
     * @param category The category to update the existing category with.
     */
    boolean updateCategoryById(Long categoryId, Category category);

    /**
     * Deletes a category from the database.
     * @param id The if of the category to delete.
     */
    void deleteCategory(Long id);

    /**
     * Gets a page from the database based on its page number and the number of elements.
     * @param pageNo The pageNo to return from the database.
     * @param noOfElements the number of elements to return at a time to the page.
     */
    Page<Category> getAllCategoriesPaged(int pageNo, int noOfElements);


    /**
     * Updates the icon of a category in the database.
     * @param categoryId The category to update the icon of.
     * @param categoryIconPath the path to the icon of the category.
     */
    void updateCategoryIconById(Long categoryId, String categoryIconPath);

    ManageCategoryDTO getManageCategoryDetails(int pageNo, int noOfElements);
}
