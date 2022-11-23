package com.qburst.spherooadmin.checklist;

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

@Service
@AllArgsConstructor
@Slf4j
public class ChecklistServiceImp implements ChecklistService{
    private ServiceRepository serviceRepository;
    private ChecklistRepository checklistRepository;
    public ResponseDTO addChecklist(ChecklistAddDTO checklistAddDTO){
        String serviceName=checklistAddDTO.getServiceName();
        String checklistName=checklistAddDTO.getName();
        if(serviceRepository.existsByServiceName(serviceName) && !(checklistRepository.existsByChecklistName(checklistName))){
            com.qburst.spherooadmin.service.Service service=serviceRepository.findByServiceName(serviceName);
            Checklist checklist=new Checklist();
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

            ResponseDTO responseDTO=new ResponseDTO();
            responseDTO.setSuccess(true);
            return responseDTO;
        }
        ResponseDTO responseDTO=new ResponseDTO();
        responseDTO.setSuccess(false);
        return responseDTO;
    }
    public Page<CheclistPagingDTO> pageChecklist(int pageNumber,int pageSize){
        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        return checklistRepository.findAll(pageable).map(ChecklistConverter::converter);
    }
    @Modifying
    @Transactional
    public ResponseDTO deleteChecklistAndChecklistItemFromId(Long id){
        if(checklistRepository.existsById(id)){
            checklistRepository.deleteById(id);
            ResponseDTO responseDTO=new ResponseDTO();
            responseDTO.setSuccess(true);
            return responseDTO;
        }
        ResponseDTO responseDTO=new ResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setMessage("Checklist NOT FOUND");
        return responseDTO;
    }


}
