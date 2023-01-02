package com.qburst.spherooadmin.search;

import com.qburst.spherooadmin.supplier.Supplier;
import com.qburst.spherooadmin.supplier.SupplierPagingConstraint;
import com.qburst.spherooadmin.supplieruser.SupplierUser;
import com.qburst.spherooadmin.supplieruser.SupplierUserType;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private Boolean asc;
    private Boolean enabledSupplier;
    private String searchName;
    @Override
    public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Supplier, SupplierUser> supplierUserJoin= root.join("supplierUsers");
        List<Predicate> predicates = new ArrayList<>();
        if (Boolean.TRUE.equals(enabledSupplier)){
            predicates.add(criteriaBuilder.equal(root.get(VISIBILITY),true));
        }
        if (Boolean.FALSE.equals(enabledSupplier)) {
            predicates.add(criteriaBuilder.equal(root.get(VISIBILITY), false));
        }
        if(!searchName.isEmpty()){
            Predicate supplierName=criteriaBuilder.like(root.get("supplierName"),"%"+searchName+"%");

            Predicate adminEmail=criteriaBuilder.and(criteriaBuilder.equal(supplierUserJoin.get("supplierUserType"), SupplierUserType.MANAGER),
                                                            criteriaBuilder.like(supplierUserJoin.get("supplierUserEmail"),"%"+searchName+"%"));
            Predicate searchPredicate=criteriaBuilder.or(supplierName,adminEmail);
            predicates.add(searchPredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
