package com.qburst.spherooadmin.checklist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qburst.spherooadmin.service.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Hameel
 * A Checklist is a list of actions for an agent to do under a service.
 * A Service can have multiple checklists.
 * A Checklist can have multiple items under it.
 */
@Entity
@Getter
@Setter
// When hibernate fetches the data these fields are included in the json which can be ignored when we serialize it.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "checklist")
@AllArgsConstructor
@NoArgsConstructor
public class Checklist {

    /**
     * The id for a Checklist
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id", nullable = false)
    private Long checklistId;

    /**
     * The name for a checklist
     */
    @Size(min = 1, max = 64)
    @Column(name = "checklist_name", nullable = false, unique = true)
    private String checklistName;

    /**
     * The description for a checklist
     */
    @Column(name = "checklist_description")
    private String checklistDescription;

    /**
     * A checklist comes under a service. We build a relation by providing the id of
     * the service as a foreign key
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    /**
     * A checklist can have multiple items under it.
     */
    @OneToMany(targetEntity = ChecklistItem.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    private List<ChecklistItem> checklistItem;
}
