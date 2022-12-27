package com.qburst.spherooadmin.category;

import org.springframework.data.domain.Page;

import java.util.List;

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
    String updateCategoryById(Long categoryId, Category category);

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
     * Gets a page of category name from the database based on its page number and the number of elements.
     * @param pageNo The pageNo to return from the database.
     * @param noOfElements the number of elements to return at a time to the page.
     * @return returns Page of category names ordered by category names.
     */
    Page<String> getAllCategoryNamesPaged(int pageNo, int noOfElements);


    /**
     * Updates the icon of a category in the database.
     * @param categoryId The category to update the icon of.
     * @param categoryIconPath the path to the icon of the category.
     */
    void updateCategoryIconById(Long categoryId, String categoryIconPath);

    Page<ManageCategoryDetails> getManageCategoryDetails(int pageNo, int noOfElements);

    /**
     * Saves multiple categories into the database from a list
     * @param categoryList the list of categories to save into the database
     */
    void saveListOfCategories(List<Category> categoryList);
    void checkCategoryName(String categoryName,long categoryId);
    void checkServiceName(String serviceName,long serviceId);
}
