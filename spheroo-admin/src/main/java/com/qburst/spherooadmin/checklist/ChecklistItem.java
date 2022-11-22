package com.qburst.spherooadmin.checklist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Hameel
 * A checklist can have multiple 'Items' under it.
 * An Item is some actions for the agent doing the service to perform in the checklist.
 */
@Entity
@Getter
@Setter
// When hibernate fetches the data these fields are included in the json which can be ignored when we serialize it.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "checklist_item")
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistItem {

    /**
     * The id for a checklist item
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_item_id")
    private long checklistItemId;

    /**
     * The description for a checklist item.
     */
    @Column(name = "checklist_item_description")
    private String checklistItemDescription;

}
