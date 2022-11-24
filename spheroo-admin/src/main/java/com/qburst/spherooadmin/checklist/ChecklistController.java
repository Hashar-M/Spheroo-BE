package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.signup.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checklist")
@AllArgsConstructor
public class ChecklistController {
    private ChecklistService checklistService;
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> postNewSupplier(@RequestBody ChecklistAddDTO checklistAddDTO){
        ResponseDTO responseDTO;
        responseDTO=checklistService.addChecklist(checklistAddDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get/all/list")
    public ResponseEntity<List<CheclistPagingDTO>> pageOfChecklistWithNoCriteria(@RequestParam("pageNo") int pageNumber, @RequestParam("pageSize") int pageSize){
        Page<CheclistPagingDTO> checlistPagingDTOPage=checklistService.pageChecklist(pageNumber,pageSize);
        if (checlistPagingDTOPage.hasContent()){
            List<CheclistPagingDTO> list=checlistPagingDTOPage.getContent();
            return ResponseEntity.ok(list);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteChecklist(@PathVariable Long id){
        ResponseDTO responseDTO;
        responseDTO=checklistService.deleteChecklistAndChecklistItemFromId(id);
        String message= responseDTO.getMessage();
        if(message.equals("Checklist NOT FOUND")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAChecklist(@PathVariable Long id) {
        Optional<Checklist> checklist = checklistService.getChecklistById(id);
      /*  if(checklist!=null){
            return new ResponseEntity<>(checklist,HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
        return new ResponseEntity<>(checklist, HttpStatus.OK);
    }

    @PutMapping("/update/{serviceName}")
    public ResponseEntity<?> updateChecklist(@RequestBody Checklist checklist,@PathVariable String serviceName){
        ResponseDTO responseDTO;
        responseDTO=checklistService.updateTheChecklist(checklist,serviceName);
        String message= responseDTO.getMessage();
        if(message.equals("NO SERVICE FOUND")){
            return new ResponseEntity<>(responseDTO,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
