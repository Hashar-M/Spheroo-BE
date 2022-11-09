package com.qburst.spherooadmin.category;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.getReferenceById(id);
    }

    @Override
    @Transactional
    public void updateCategoryById(Long categoryId, Category category) {
        categoryRepository.updateCategoryById(category.getCategoryName(), category.getCategoryDescription(), category.getCategoryIcon(), categoryId);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
       categoryRepository.deleteByCategoryId(id);
    }

    @Override
    public Page<Category> getAllCategoriesPaged(int pageNo, int noOfElements) {
        Pageable pageWithRequiredElements = PageRequest.of(pageNo, noOfElements, Sort.by("categoryId"));
        return categoryRepository.findAll(pageWithRequiredElements);
    }

    @Transactional
    @Override
    public void updateCategoryIconById(Long categoryId, String categoryIconPath) {
        categoryRepository.updateCategoryIconByCategoryId(categoryIconPath, categoryId);
    }
}
