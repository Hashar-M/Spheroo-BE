package com.qburst.spherooadmin.checklist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  interface provides the mechanism for storage, retrieval,
 *  update, delete and search operation on {@link Checklist}.
 */
@Repository
public interface ChecklistRepository extends JpaRepository<Checklist,Long> {
    /**
     * Check if the checklist exists by checklist name
     * @param checklistName Name of the checklist
     * @return Boolean true if it exists and false if it does not
     */
    boolean existsByChecklistName(String checklistName);

    /**
     * Find checklist by its name
     * @param checklistName Name of the checklist
     * @param pageable pageable object
     * @return Page of Checklist
     */
    Page<Checklist> findByChecklistName(String checklistName, Pageable pageable);
}
