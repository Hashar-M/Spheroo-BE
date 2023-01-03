package com.qburst.spherooadmin.search;

import com.qburst.spherooadmin.orderDetails.Orders;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hameel
 * The OrderFilter implements a specification which allows us to build predicates
 * or criteria to search for Orders in the order table.
 */
@Data
public class OrderFilter implements Specification<Orders> {

    /**
     * The name of the service to search for
     */
    private String serviceName;
    /**
     * The zip code of the placed order you want to search for
     */
    private String zipCode;
    /**
     * The delivery from date of the order
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date fromDate;
    /**
     * The delivery to date of the order
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date toDate;

    /**
     * Builds a predicate object by which hibernate can use to build a JPQL
     * query to select the orders from the database based on some required criteria.
     */
    @Override
    public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (serviceName != null) {
            predicates.add(criteriaBuilder.like(root.get("serviceName"), "%" + serviceName + "%"));
        }
        if (zipCode != null) {
            predicates.add(criteriaBuilder.like(root.get("zipCode"), "%" + zipCode + "%"));
        }
        if (fromDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("deliveryToDate"), fromDate));
        }
        if (toDate != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deliveryToDate"), toDate));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
