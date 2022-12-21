package com.qburst.spherooadmin.search;

import com.qburst.spherooadmin.supplier.Supplier;
import com.qburst.spherooadmin.supplier.SupplierPagingConstraint;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.qburst.spherooadmin.constants.SupplierModelConstants.VISIBILITY;

/**
 * The SupplierPaginationFilter implements a specification which allows us to build predicates
 * or criteria to search for Orders in the Supplier table.
 */
@Data
public class SupplierPaginationFilter implements Specification<Supplier> {
    private SupplierPagingConstraint key;
    @NotNull
    private Boolean asc;
    private Boolean enabledSupplier;
    @Override
    public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (Boolean.TRUE.equals(enabledSupplier)){
            predicates.add(criteriaBuilder.equal(root.get(VISIBILITY),true));
        }
        if (Boolean.FALSE.equals(enabledSupplier)) {
            predicates.add(criteriaBuilder.equal(root.get(VISIBILITY), false));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
