package com.qburst.spherooadmin.orderDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RejectReasonRepository extends JpaRepository<RejectReason,Long> {
}
