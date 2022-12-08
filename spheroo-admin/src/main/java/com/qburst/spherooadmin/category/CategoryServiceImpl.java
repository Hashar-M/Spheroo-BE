package com.qburst.spherooadmin.category;

import com.qburst.spherooadmin.service.ServiceChargeRepository;
import com.qburst.spherooadmin.service.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.qburst.spherooadmin.constants.CategoryConstants.CATEGORY_NAME;

/**
 * @inheritDoc
 *
 * Implements the category service interface.
 */
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;
    private ServiceRepository serviceRepository;
    private ServiceChargeRepository serviceChargeRepository;

    @Override
    @Transactional
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) {
        if(categoryRepository.existsById(id)){
            return categoryRepository.getReferenceById(id);
        }
        throw new EntityNotFoundException("No category exist with given id");
    }

    @Override
    @Transactional
    public String updateCategoryById(Long categoryId, Category category) {
        boolean isExist = categoryRepository.existsById(categoryId);
        if(isExist){
            category.setCategoryId(categoryId);
            categoryRepository.save(category);
            List<Long> noReferenceChargeIds = serviceChargeRepository.findNullServiceCharges();
            serviceChargeRepository.deleteAllById(noReferenceChargeIds);
            List<Long> noReferenceServiceIds = serviceRepository.findNullCategoryServices();
            serviceRepository.deleteAllById(noReferenceServiceIds);
            return "Category Updated successfully";
        }else {
            throw new EntityNotFoundException("No category exist with given id");
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteByCategoryId(id);
        }
       throw new EntityNotFoundException("No category exist with given id");
    }

    @Override
    public Page<String> getAllCategoryNamesPaged(int pageNo, int noOfElements) {
        Pageable pageWithRequiredElements = PageRequest.of(pageNo,noOfElements,Sort.by("category_name"));
        return categoryRepository.getCategoryNamesPaged(pageWithRequiredElements);
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
    @Override
    public Page<ManageCategoryDetails> getManageCategoryDetails(int pageNo, int noOfElements) {
        Pageable pageableCriteria = PageRequest.of(pageNo, noOfElements, Sort.by(CATEGORY_NAME));
        Page<ManageCategoryDetails> manageCategoryDetailsPage = categoryRepository.getManageCategoryPaged(pageableCriteria);
        manageCategoryDetailsPage.forEach(item->{
            item.setNoOfServices(categoryRepository.getReferenceById(item.getCategoryId()).getServiceList().size());
        });
        return manageCategoryDetailsPage;
    }

    /**
     * Saves multiple categories into the database from a list
     * @param categoryList the list of categories to save into the database
     */
    @Override
    public void saveListOfCategories(List<Category> categoryList) {
        categoryRepository.saveAll(categoryList);
    }
}
