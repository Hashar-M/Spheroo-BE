package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.signup.ResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChecklistService {
    public ResponseDTO addChecklist(ChecklistAddDTO checklistAddDTO);
    public Page<CheclistPagingDTO> pageChecklist(int pageNumber, int pageSize);
    public ResponseDTO deleteChecklistAndChecklistItemFromId(Long id);
    public Checklist getChecklistById(Long id);
    public ResponseDTO updateTheChecklist(Checklist checklist);
}
