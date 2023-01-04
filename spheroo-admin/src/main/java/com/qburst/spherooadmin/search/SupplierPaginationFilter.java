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

import static com.qburst.spherooadmin.constants.SpherooConstants.PERCENT_SIGN;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.SUPPLIER_NAME;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.SUPPLIER_USERS;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.SUPPLIER_USER_EMAIL;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.SUPPLIER_USER_TYPE;
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
        List<Predicate> predicates = new ArrayList<>();
        if (Boolean.TRUE.equals(enabledSupplier)){
            predicates.add(criteriaBuilder.equal(root.get(VISIBILITY),true));
        }
        if (Boolean.FALSE.equals(enabledSupplier)) {
            predicates.add(criteriaBuilder.equal(root.get(VISIBILITY), false));
        }
        if(!searchName.isEmpty()){
            Join<Supplier, SupplierUser> supplierUserJoin= root.join(SUPPLIER_USERS);
            Predicate supplierName=criteriaBuilder.and(criteriaBuilder.like(root.get(SUPPLIER_NAME),PERCENT_SIGN+searchName+PERCENT_SIGN),
                    criteriaBuilder.equal(supplierUserJoin.get(SUPPLIER_USER_TYPE), SupplierUserType.MANAGER));

            Predicate adminEmail=criteriaBuilder.and(criteriaBuilder.equal(supplierUserJoin.get(SUPPLIER_USER_TYPE), SupplierUserType.MANAGER),
                                                            criteriaBuilder.like(supplierUserJoin.get(SUPPLIER_USER_EMAIL),PERCENT_SIGN+searchName+PERCENT_SIGN));
            Predicate searchPredicate=criteriaBuilder.or(supplierName,adminEmail);
            predicates.add(searchPredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
