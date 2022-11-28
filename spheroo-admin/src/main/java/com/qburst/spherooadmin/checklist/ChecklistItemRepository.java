package com.qburst.spherooadmin.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * interface provides the mechanism for storage, retrieval,
 * update, delete and search operation on {@link ChecklistItem}.
 */
@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem,Long> {
    /**
     * method for finding a list of ID for {@link ChecklistItem} where corresponding {@link ChecklistItem}
     * has no {@link Checklist} linked.
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT checklist_item_id FROM checklist_item WHERE checklist_id IS NULL")
    List<Long> findByChecklistIdNull();
}
