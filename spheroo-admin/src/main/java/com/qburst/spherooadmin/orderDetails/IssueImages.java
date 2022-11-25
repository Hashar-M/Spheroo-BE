package com.qburst.spherooadmin.orderDetails;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "issue_attached_images")
public class IssueImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pictureId;
    @Column(name = "issue_attached_image")
    private String issueImages;
}
