package com.qburst.spherooadmin.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qburst.spherooadmin.service.Service;
import com.qburst.spherooadmin.supplier.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import java.util.List;


/**
 * Represents the Category entity.
 * A Category represents the types of services provided by a supplier
 * E.g. Electrical, plumbing, etc.
 */
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
<<<<<<< HEAD

    @OneToMany(targetEntity = Service.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private List<Service> serviceList;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Supplier> suppliers;
=======
>>>>>>> a90c63f (Provided documentation comments)
}
