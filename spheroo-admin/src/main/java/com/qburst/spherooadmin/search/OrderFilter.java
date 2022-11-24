package com.qburst.spherooadmin.search;

import com.qburst.spherooadmin.orderDetails.Orders;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderFilter implements Specification<Orders> {

    private String serviceName;
    private String zipcode;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;

    @Override
    public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (serviceName != null) {
            predicates.add(criteriaBuilder.like(root.get("serviceName"), "%" + serviceName + "%"));
        }
        if (zipcode != null) {
            predicates.add(criteriaBuilder.like(root.get("zipCode"), "%" + zipcode + "%"));
        }
        if (fromDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deliveryFromDate"), toDate));
        }
        if (toDate != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deliveryToDate"), fromDate));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
