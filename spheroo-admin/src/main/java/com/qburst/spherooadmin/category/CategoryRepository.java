package com.qburst.spherooadmin.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * The repository for Category entity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Updates an existing Category based on its id.
     * @param categoryName Updated name of the category.
     * @param categoryDescription Updated description of the category.
     * @param categoryIcon Updated icon of the category.
     * @param categoryId The id of the category to update.
     */
    @Modifying
    @Query("UPDATE Category c SET c.categoryName = ?1, c.categoryDescription = ?2, c.categoryIcon =?3 WHERE c.categoryId = ?4")
    void updateCategoryById(String categoryName, String categoryDescription, String categoryIcon, Long categoryId);

    /**
     * Delete the category specified by its id.
     * @param categoryId the id of the category to delete.
     */
    void deleteByCategoryId(Long categoryId);

    /**
     * Updates the icon of a category specified by its id.
     * @param categoryIconPath the path to the category icon.
     * @param categoryId the id of the category to update.
     */
    @Modifying
    @Query("UPDATE Category c SET c.categoryIcon = ?1 WHERE c.categoryId = ?2")
    void updateCategoryIconByCategoryId(String categoryIconPath, Long categoryId);
}
