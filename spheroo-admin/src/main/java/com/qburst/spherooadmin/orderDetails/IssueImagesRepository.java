package com.qburst.spherooadmin.orderDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueImagesRepository extends JpaRepository<IssueImages,Long> {

    @Query(value = "SELECT * FROM issue_attached_images WHERE order_id =?1",nativeQuery = true)
    List<IssueImages> findIssueImagesByOrderId(long orderId);
}
