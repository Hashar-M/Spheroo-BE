package com.qburst.spherooadmin.orderDetails;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrdersDisplayDTOPage {
    private List<OrdersDisplayDTO> content;
    private int totalPages;
    private int pageSize;
    private int pageNumber;
    private long totalElements;
}
