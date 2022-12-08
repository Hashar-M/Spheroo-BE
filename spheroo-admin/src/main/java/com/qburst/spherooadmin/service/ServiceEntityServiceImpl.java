package com.qburst.spherooadmin.service;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.exception.ServiceNameConstraintException;
import com.qburst.spherooadmin.signup.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.qburst.spherooadmin.constants.CategoryConstants.CATEGORY_NOT_FOUND;
import static com.qburst.spherooadmin.constants.ResponseConstants.SERVICE_NAME_ALREADY_IN_USE;

@Slf4j
@Service
@AllArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService {


    /**
     * The repository object of the Service entity
     */
    private ServiceRepository serviceRepository;
    private CategoryRepository categoryRepository;
    private ServiceChargeRepository serviceChargeRepository;

    /**
     * Returns a service by its id
     * @param id ID of the service to return
     * @return Service of the specified ID
     */
    @Override
    public Optional<com.qburst.spherooadmin.service.Service> getServiceById(Long id) {
        if (serviceRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
    }
        return serviceRepository.findById(id);
    }

    /**
     * Stores a new service into the database
     * @param service The service entity to store
     */
    @Override
    public void saveService(com.qburst.spherooadmin.service.Service service) {
        serviceRepository.save(service);
    }

    /**
     * Deletes a service based on its ID
     * @param id The id of the service to delete
     */
    @Override
    public void deleteServiceById(Long id) {
        serviceRepository.deleteByServiceId(id);
    }

    /**
     * Update the given {@link com.qburst.spherooadmin.service.Service} using {@link ServiceRepository}
     * @param service tobe updated.
     */
    @Transactional
    @Override
    public void updateASingleService(com.qburst.spherooadmin.service.Service service) {
        if (Objects.equals(serviceRepository.findServiceNameByServiceId(service.getServiceId()), service.getServiceName()) || !serviceRepository.existsByServiceName(service.getServiceName())) {
            serviceRepository.save(service);
            serviceChargeRepository.deleteAllById(serviceChargeRepository.findNullServiceCharges());
            return;
        }
        throw new ServiceNameConstraintException(SERVICE_NAME_ALREADY_IN_USE);
    }

    /**
     * Returns a page of services for the specified category
     * @param category_id the id of the category to return services of
     * @param pageNo pageable object
     * @param qty Quantity of entities to return at a time
     * @return Page with the needed details
     */
    @Override
    public Page<com.qburst.spherooadmin.service.Service> getServiceByCategoryId(Long category_id, int pageNo, int qty) {
        Pageable pageableCriteria = PageRequest.of(pageNo, qty);
        return serviceRepository.getServicesByCategoryId(category_id, pageableCriteria);
    }

    /**
     * Gets the names of services under a category specified by its ID.
     * @param category_id The id of the category under whose services you want to get.
     * @return a List of strings containing the names of services under the category
     */
    @Override
    public List<String> getServiceNameByCategoryId(Long category_id) {
        return serviceRepository.getServiceNameByCategoryId(category_id);
    }

    /**
     * From {@link ServiceRepository} {@link Page} for service names fetched.
     * @param categoryName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public ResponseDTO getListOfServiceNamesForTheGivenCategory(String categoryName,int pageNo, int pageSize){
        ResponseDTO responseDTO=new ResponseDTO();
        if(pageNo<0 || pageSize<0){
            throw new IllegalArgumentException();
        }
        else if(categoryRepository.existsByCategoryName(categoryName)) {
            /**
             * category id is taken from given category name.
             */
            long categoryId = categoryRepository.getCategoryIdFromCategoryName(categoryName);
            /**
             * {@link Pageable} object is creating for the given page number and size.
             */
            Pageable pageable=PageRequest.of(pageNo,pageSize);
            /**
             *{@link Page} from repository is added to {@link ResponseDTO}.
             */
            responseDTO.setData(serviceRepository.findServiceNameForACategory(categoryId,pageable));
            responseDTO.setSuccess(true);
            return responseDTO;
        }
        /**
         * in case of given category doesn't exists a message is adding to {@link ResponseDTO}
         */
        else{
            responseDTO.setSuccess(false);
            responseDTO.setMessage(CATEGORY_NOT_FOUND);
            return responseDTO;
        }
    }

    /**
     * Saves multiple services into the database from a list
     * @param serviceList the list of services to save into the database
     */
    @Override
    public void saveListOfServices(List<com.qburst.spherooadmin.service.Service> serviceList) {
        serviceRepository.saveAll(serviceList);
    }
}
