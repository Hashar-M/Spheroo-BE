package com.qburst.spherooadmin.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist,Long> {
    public boolean existsByChecklistName(String checklistName);
}
