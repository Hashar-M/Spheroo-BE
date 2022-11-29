package com.qburst.spherooadmin.service;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.signup.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.qburst.spherooadmin.constants.CategoryConstants.CATEGORY_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService {


    /**
     * The repository object of the Service entity
     */
    private ServiceRepository serviceRepository;
    private CategoryRepository categoryRepository;

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
     * Update an existing service according to its ID
     * @param id the id of the service to update
     */
    @Override
    public void updateServiceById(String serviceName, String description, Boolean variablePrice, List<ServiceCharge> serviceChargeList, Long id) {
        serviceRepository.updateService(serviceName, description, variablePrice, serviceChargeList, id);
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
}
