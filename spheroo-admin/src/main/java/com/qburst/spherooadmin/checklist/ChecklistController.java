package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.signup.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<CheclistPagingDTO>> pageOfChecklistWithNoCriteria(@RequestParam("pageNo") int pageNumber, @RequestParam("/pageSize") int pageSize){
        Page<CheclistPagingDTO> checlistPagingDTOPage=checklistService.pageChecklist(pageNumber,pageSize);
        if (checlistPagingDTOPage.hasContent()){
            List<CheclistPagingDTO> list=checlistPagingDTOPage.getContent();
            return ResponseEntity.ok(list);
        }
        return null;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteChecklist(@PathVariable Long id){
        ResponseDTO responseDTO;
        responseDTO=checklistService.deleteChecklistAndChecklistItemFromId(id);
        if(responseDTO.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAChecklist(@PathVariable Long id){
        Checklist checklist=checklistService.getChecklistById(id);
        if(checklist!=null){
            return new ResponseEntity<>(checklist,HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateChecklist(@RequestBody Checklist checklist){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
