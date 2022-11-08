package com.qburst.spherooadmin.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
// When hibernate fetches the data these fields are included in the json which can be ignored when we serialize it.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long categoryId;
    @Column(name = "category_name", length = 64, nullable = false, unique = true)
    private String categoryName;
    @Column(name = "category_icon")
    private String categoryIcon;
    @Column(name = "category_description", length = 1024)
    private String categoryDescription;
}
