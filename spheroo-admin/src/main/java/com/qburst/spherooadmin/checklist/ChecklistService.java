package com.qburst.spherooadmin.checklist;

import com.qburst.spherooadmin.signup.ResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * interface have abstract methods for service layer for {@link Checklist} and {@link ChecklistItem}.
 */
public interface ChecklistService {
    public ResponseDTO addChecklist(ChecklistAddDTO checklistAddDTO);
    public Page<CheclistPagingDTO> pageChecklist(int pageNumber, int pageSize);
    public ResponseDTO deleteChecklistAndChecklistItemFromId(Long id);
    public ChecklistGetDTO getChecklistById(Long id);
    public ResponseDTO updateTheChecklist(Checklist checklist,String serviceName);
}
