package com.qburst.spherooadmin.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  interface provides the mechanism for storage, retrieval,
 *  update, delete and search operation on {@link Checklist}.
 */
@Repository
public interface ChecklistRepository extends JpaRepository<Checklist,Long> {
    public boolean existsByChecklistName(String checklistName);
}
