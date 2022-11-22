package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.signup.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
