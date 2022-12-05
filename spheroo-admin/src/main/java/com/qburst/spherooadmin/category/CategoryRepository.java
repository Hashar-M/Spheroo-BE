package com.qburst.spherooadmin.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    boolean existsByCategoryName(String categoryName);
    @Query(nativeQuery = true,value = "SELECT category_id FROM category WHERE category.category_name=?1")
    long getCategoryIdFromCategoryName(String categoryName);

    /**
     * for finding a {@link Category} from the category name.
     * @param categoryId
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT category_name FROM category WHERE category_id=?1")
    String getCategoryNameFromCategoryId(long categoryId);

    @Query(value = "SELECT category_name FROM category", nativeQuery = true)
    Page<String> getCategoryNamesPaged(Pageable pageable);
    @Query(value = "SELECT new com.qburst.spherooadmin.category.ManageCategoryDetails(cat.categoryId, cat.categoryName) " +
            "FROM Category cat ")
    Page<ManageCategoryDetails> getManageCategoryPaged(Pageable pageable);
}
