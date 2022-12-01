package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.signup.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.qburst.spherooadmin.constants.ChecklistConstants.SERVICE_NOT_FOUND;

/**
 * @author Akhilesh
 * controller for checklist and checklist items.
 */

@RestController
@RequestMapping("/checklist")
@AllArgsConstructor
public class ChecklistController {
    private ChecklistService checklistService;

    /**
     * method for creating a new checklist
     * @param checklistAddDTO posses data of a new {@link Checklist } and {@link ChecklistItem}
     * @return 200 OK status code for successfully creation of new {@link Checklist}
     */
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> postNewSupplier(@RequestBody ChecklistAddDTO checklistAddDTO){
        ResponseDTO responseDTO;
        responseDTO=checklistService.addChecklist(checklistAddDTO);
        if (responseDTO.isSuccess()) {
            return ResponseEntity.ok(responseDTO);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method gives page for checklist in order in which they are created without any sorting parameter.
     * @param pageNumber
     * @param pageSize
     * @return {@link Page} for checklist.The content of page is a list of {@link ChecklistPagingDTO}.
     */
    @GetMapping("/get/all/list")
    public ResponseEntity<Page<ChecklistPagingDTO>> pageOfChecklistWithNoCriteria(@RequestParam("pageNo") int pageNumber, @RequestParam("pageSize") int pageSize){
        Page<ChecklistPagingDTO> checlistPagingDTOPage=checklistService.pageChecklist(pageNumber,pageSize);
        if (checlistPagingDTOPage.hasContent()){
            return ResponseEntity.ok(checlistPagingDTOPage);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    /**
     * Search for a checklist by its name
     * @param pageNumber pageNumber of the search results
     * @param pageSize Size of the page
     * @param name name of the checklist
     * @return Page of ChecklistPagingDTOs
     */
    @GetMapping("/get/all/search")
    public ResponseEntity<Page<ChecklistPagingDTO>> pageOfChecklistWithSpecifiedCriteria(@RequestParam("pageNo") int pageNumber, @RequestParam("pageSize") int pageSize,
                                                                                         @RequestParam("name") String name){
        Page<ChecklistPagingDTO> checklistPagingDTOPage=checklistService.findChecklistByName(name, pageNumber, pageSize);
        if (checklistPagingDTOPage.hasContent()){
            return ResponseEntity.ok(checklistPagingDTOPage);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    /**
     * Delete a {@link Checklist} using corresponding ID value.
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteChecklist(@PathVariable Long id){
        ResponseDTO responseDTO;
        responseDTO=checklistService.deleteChecklistAndChecklistItemFromId(id);
        if(responseDTO.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.NOT_FOUND);
    }

    /**
     * methods return a {@link  Checklist} for corresponding ID value.
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAChecklist(@PathVariable Long id) {
        ChecklistGetDTO checklistGetDTO = checklistService.getChecklistById(id);
        if(checklistGetDTO!=null) {
            return new ResponseEntity<>(checklistGetDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Updation of a {@link Checklist} and {@link  ChecklistItem}.
     * @param checklist
     * @param serviceName for {@link  com.qburst.spherooadmin.service.Service} of updated checklist.
     * @return
     */

    @PutMapping("/update/{serviceName}")
    public ResponseEntity<?> updateChecklist(@RequestBody Checklist checklist,@PathVariable String serviceName){
        ResponseDTO responseDTO;
        responseDTO=checklistService.updateTheChecklist(checklist,serviceName);
        String message= responseDTO.getMessage();
        if(message!=null && message.equals(SERVICE_NOT_FOUND)){
            return new ResponseEntity<>(responseDTO,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
