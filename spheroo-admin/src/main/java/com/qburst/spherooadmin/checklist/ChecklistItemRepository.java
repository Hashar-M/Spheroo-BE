package com.qburst.spherooadmin.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem,Long> {
    @Query(nativeQuery = true,value = "SELECT checklist_item_id FROM checklist_item WHERE checklist_id IS NULL")
    List<Long> findByChecklistIdNull();
}
