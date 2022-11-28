package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.service.ServiceRepository;
import com.qburst.spherooadmin.signup.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.qburst.spherooadmin.constants.ChecklistConstants.CHECKLIST_NOT_FOUND;
import static com.qburst.spherooadmin.constants.ChecklistConstants.SERVICE_NOT_FOUND;

/**
 * {@inheritDoc}
 * service layer for {@link Checklist} and {@link ChecklistItem}
 */
@Service
@AllArgsConstructor
@Slf4j
public class ChecklistServiceImpl implements ChecklistService{
    private ServiceRepository serviceRepository;
    private ChecklistRepository checklistRepository;
    private ChecklistConverter checklistConverter;
    private ChecklistItemRepository checklistItemRepository;
    private CategoryRepository categoryRepository;

    /**
     * method for creating new {@link Checklist}
     * @param checklistAddDTO
     * @return
     */
    @Transactional
    public ResponseDTO addChecklist(ChecklistAddDTO checklistAddDTO){
        String serviceName=checklistAddDTO.getServiceName();
        String checklistName=checklistAddDTO.getName();
        /**
         * {@code if(serviceRepository.existsByServiceName(serviceName) && !(checklistRepository.existsByChecklistName(checklistName)))}
         * check for given service name and checklist name to make sure one{@link com.qburst.spherooadmin.service.Service} with the name exists and the checklist name never used before.
         * Only if the conditions satisfied new {@link Checklist} is created.
         */
        if(serviceRepository.existsByServiceName(serviceName) && !(checklistRepository.existsByChecklistName(checklistName))){
            com.qburst.spherooadmin.service.Service service=serviceRepository.findByServiceName(serviceName);
            Checklist checklist=new Checklist();
            /**
             * below create a new {@link Checklist} entity from given DTO data.
             */
            List<ChecklistItem> checklistItems=new ArrayList<>();

            checklistAddDTO.getChecklistItems().forEach(checklistItemAddDTO -> {
                ChecklistItem checklistItem=new ChecklistItem();
                checklistItem.setChecklistItemDescription(checklistItemAddDTO.getDescription());
                checklistItems.add(checklistItem);
            });

            checklist.setChecklistName(checklistAddDTO.getName());
            checklist.setChecklistDescription(checklistAddDTO.getDescription());
            checklist.setService(service);
            checklist.setChecklistItem(checklistItems);
            /**
             * below code buld a new response DTO.
             */

            checklistRepository.save(checklist);
            ResponseDTO responseDTO=new ResponseDTO();
            responseDTO.setSuccess(true);
            return responseDTO;
        }
        /**
         * The reasons cause to not to create the {@link Checklist} is mentioned in the below DTO.
         */
        ResponseDTO responseDTO=new ResponseDTO();
        responseDTO.setSuccess(false);
        return responseDTO;
    }

    /**
     * method for ipliment pagination.
     * @param pageNumber
     * @param pageSize
     * @return {@link Page} of {@link CheclistPagingDTO}
     */
    public Page<CheclistPagingDTO> pageChecklist(int pageNumber,int pageSize){
        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        return checklistRepository.findAll(pageable).map(checklistConverter::converter);
    }

    /**
     * Delete a {@link Checklist} using corresponding ID value.
     * @param id
     * @return
     */
    @Modifying
    @Transactional
    public ResponseDTO deleteChecklistAndChecklistItemFromId(Long id){
        /**
         * {@code if(checklistRepository.existsById(id))} verifies if {@link Checklist} of give id exists.
         */
        if(checklistRepository.existsById(id)){
            checklistRepository.deleteById(id);

            ResponseDTO responseDTO=new ResponseDTO();
            responseDTO.setSuccess(true);
            return responseDTO;
        }
        ResponseDTO responseDTO=new ResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setMessage(CHECKLIST_NOT_FOUND);
        return responseDTO;
    }

    /**
     * Return a {@link Checklist} by corresponding ID value.
     * @param id
     * @return
     */
    public ChecklistGetDTO getChecklistById(Long id){
        Optional<Checklist> optionalChecklist=checklistRepository.findById(id);
        ChecklistGetDTO checklistGetDTO = new ChecklistGetDTO();
        if(optionalChecklist.isPresent()) {
            Checklist checklist = optionalChecklist.get();
            long categoryId = serviceRepository.findCategoryIdFromServiceId(checklist.getService().getServiceId());
            String categoryName = categoryRepository.getCategoryNameFromCategoryId(categoryId);
            String serviceName = checklist.getService().getServiceName();

            checklist.setService(null);
            checklistGetDTO.setChecklist(checklist);
            checklistGetDTO.setServiceName(serviceName);
            checklistGetDTO.setCategoryName(categoryName);
            return checklistGetDTO;
        }
        return null;
    }

    /**
     *method update  a {@link Checklist} and {@link  ChecklistItem}.
     * @param checklist
     * @param serviceName
     * @return
     */
    @Modifying
    @Transactional
    public ResponseDTO updateTheChecklist(Checklist checklist,String serviceName){
        if (serviceRepository.existsByServiceName(serviceName)){
            com.qburst.spherooadmin.service.Service service=serviceRepository.findByServiceName(serviceName);
            checklist.setService(service);
            checklistRepository.save(checklist);

            List<Long> checklistItemIds= checklistItemRepository.findByChecklistIdNull();
            checklistItemRepository.deleteAllById(checklistItemIds);

            ResponseDTO responseDTO=new ResponseDTO();
            responseDTO.setSuccess(true);
            return responseDTO;
        }
        /**
         * below code create a response DTO contains data mentioning the reason for not to update the {@link Checklist}
         */
        ResponseDTO responseDTO=new ResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setMessage(SERVICE_NOT_FOUND);
        return responseDTO;
    }

}
