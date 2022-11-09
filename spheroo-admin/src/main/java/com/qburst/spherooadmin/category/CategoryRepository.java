package com.qburst.spherooadmin.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Query("UPDATE Category c SET c.categoryName = ?1, c.categoryDescription = ?2, c.categoryIcon =?3 WHERE c.categoryId = ?4")
    void updateCategoryById(String categoryName, String categoryDescription, String categoryIcon, Long categoryId);

    void deleteByCategoryId(Long categoryId);

    @Modifying
    @Query("UPDATE Category c SET c.categoryIcon = ?1 WHERE c.categoryId = ?2")
    void updateCategoryIconByCategoryId(String categoryIconPath, Long categoryId);
}
