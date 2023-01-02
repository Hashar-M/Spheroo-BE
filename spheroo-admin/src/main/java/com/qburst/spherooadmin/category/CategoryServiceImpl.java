package com.qburst.spherooadmin.category;

import com.qburst.spherooadmin.exception.UniqueConstraintViolationException;
import com.qburst.spherooadmin.exception.WrongDataForActionException;
import com.qburst.spherooadmin.service.ServiceChargeRepository;
import com.qburst.spherooadmin.service.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        try{
            categoryRepository.save(category);
        }catch(DataIntegrityViolationException exception){
            String errorMessage = exception.getMostSpecificCause().toString();
            if(errorMessage.contains("violates unique constraint")){
                if(errorMessage.contains("(category_name)")){
                    int start = errorMessage.indexOf("=(")+2;
                    int end =errorMessage.indexOf(") already exists");
                    throw new UniqueConstraintViolationException("Category : '"+ errorMessage.substring(start,end)+"' already exists.");
                }
                if(errorMessage.contains("(service_name)")){
                    int start = errorMessage.indexOf("=(")+2;
                    int end =errorMessage.indexOf(") already exists");
                    throw new UniqueConstraintViolationException("Service : '"+ errorMessage.substring(start,end)+"' already exists.");
                }
            }
            throw new WrongDataForActionException(errorMessage);
        }
    }

    @Override
    public Category getCategory(Long id) {
        if(categoryRepository.existsById(id)){
            return categoryRepository.getReferenceById(id);
        }else {
            throw new EntityNotFoundException("No category exist with given id");
        }
    }

    @Override
    @Transactional
    public void updateCategoryById(Long categoryId, Category category) {
        boolean isExist = categoryRepository.existsById(categoryId);
        if(isExist){
            category.setCategoryId(categoryId);
            saveCategory(category);
            List<Long> noReferenceChargeIds = serviceChargeRepository.findNullServiceCharges();
            serviceChargeRepository.deleteAllById(noReferenceChargeIds);
            List<Long> noReferenceServiceIds = serviceRepository.findNullCategoryServices();
            serviceRepository.deleteAllById(noReferenceServiceIds);
        }else {
            throw new EntityNotFoundException("No category exist with given id");
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteByCategoryId(id);
        }else{
            throw new EntityNotFoundException("No category exist with given id");
        }
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

    /**
     * function for checking whether the given category name is acceptable or not.
     * @param categoryName accepts given category name.
     * @param categoryId accepts category id
     */
    @Override
    public void checkCategoryName(String categoryName, long categoryId) {
        //checking whether it is new category adding or updating
        if(categoryId==0) {
            if (categoryRepository.existsByCategoryName(categoryName)) {
                throw new UniqueConstraintViolationException("Category : '" + categoryName + "' already exists.");
            }
        }else{
            if (categoryRepository.existsById(categoryId)){
                //checking category name unique or not
                if(categoryRepository.existsByCategoryName(categoryName)){
                    //conforming the existence not from its previous name.(case: category name not changed)
                    if(!categoryRepository.getReferenceById(categoryId).getCategoryName().equals(categoryName)){
                        throw new UniqueConstraintViolationException("Category : '" + categoryName + "' already exists.");
                    }
                }
            }else{
                throw new EntityNotFoundException("category not found");
            }
        }
    }

    /**
     * function for checking whether the given service name is acceptable or not.
     * @param serviceName accepts the given service name.
     * @param serviceId accepts the service id.
     */
    @Override
    public void checkServiceName(String serviceName,long serviceId) {
        if(serviceId==0){
            if(serviceRepository.existsByServiceName(serviceName)){
                throw new UniqueConstraintViolationException("Service : '"+serviceName+"' already exists.");
            }
        }else{
            if (serviceRepository.existsById(serviceId)){
                //checking service name unique or not
                if(serviceRepository.existsByServiceName(serviceName)){
                    //conforming the existence not from its previous name.(case: service name not changed)
                    if(!serviceRepository.getReferenceById(serviceId).getServiceName().equals(serviceName)){
                        throw new UniqueConstraintViolationException("Service : '" + serviceName + "' already exists.");
                    }
                }
            }else{
                throw new EntityNotFoundException("service not found");
            }
        }
    }
}
