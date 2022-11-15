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

import java.util.ArrayList;
import java.util.List;

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
        return categoryRepository.getReferenceById(id);
    }

    @Override
    @Transactional
    public boolean updateCategoryById(Long categoryId, Category category) {
//        categoryRepository.updateCategoryById(category.getCategoryName(), category.getCategoryDescription(), category.getCategoryIcon(), categoryId);
        boolean isExist = categoryRepository.existsById(categoryId);
        if(isExist){
            category.setCategoryId(categoryId);
            categoryRepository.save(category);
            List<Long> noReferenceChargeIds = serviceChargeRepository.findNullServiceCharges();
            serviceChargeRepository.deleteAllById(noReferenceChargeIds);
            List<Long> noReferenceServiceIds = serviceRepository.findNullCategoryServices();
            serviceRepository.deleteAllById(noReferenceServiceIds);
            return true;
        }else {
            return false;
        }

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
    @Override
    public ManageCategoryDTO getManageCategoryDetails(int pageNo, int noOfElements) {
        Pageable pageableCriteria = PageRequest.of(pageNo, noOfElements, Sort.by("categoryName"));
        Page<Category> categoryPage = categoryRepository.findAll(pageableCriteria);

        List<ManageCategoryDetails> manageCategoryDetailsList = new ArrayList<>();
        categoryPage.forEach(category -> manageCategoryDetailsList.add(new ManageCategoryDetails(category.getCategoryId(),category.getCategoryName(),category.getServiceList().size())));
        ManageCategoryDTO manageCategoryDTO = new ManageCategoryDTO();
        manageCategoryDTO.setPage(pageNo);
        manageCategoryDTO.setNoOfElements(noOfElements);
        manageCategoryDTO.setManageCategoryDetailsList(manageCategoryDetailsList);
        return manageCategoryDTO;
    }
}
