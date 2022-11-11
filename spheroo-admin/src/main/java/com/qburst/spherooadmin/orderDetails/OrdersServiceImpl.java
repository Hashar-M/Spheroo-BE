package com.qburst.spherooadmin.orderDetails;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService
{
    private OrdersRepository ordersRepo;


    @Override
    public Orders getOrderById(long id) {
        return ordersRepo.getReferenceById(id);
    }

    @Override
    public List<Orders> getOrderByEscalation(int escalationPeriod) {
        return null;
    }

    @Override
    public void addOrder(Orders order)
    {
        ordersRepo.save(order);
    }

    @Override
    public Page<Orders> getAllOrdersPaged(int pageNo, int noOfElements,String columnToSort,boolean isAsc,String status)
    {
        Pageable pageWithRequiredElements;
        if(isAsc)
        {
            pageWithRequiredElements = PageRequest.of(pageNo,noOfElements, Sort.by(columnToSort));
        }
        else
        {
            pageWithRequiredElements = PageRequest.of(pageNo,noOfElements, Sort.by(columnToSort).descending());
        }
        if(status.equalsIgnoreCase("open"))
        {
            return ordersRepo.findByOpenOrderStatus(pageWithRequiredElements);

        } else if (status.equalsIgnoreCase("closed"))
        {
            return ordersRepo.findByClosedOrderStatus(pageWithRequiredElements);
        } else if (status.equalsIgnoreCase("overdue"))
        {
            return ordersRepo.getOrderByDuePeriod(2,pageWithRequiredElements); // 2 days from due date considered as overdue

        } else
        {

            return ordersRepo.getOrderByDuePeriod(4,pageWithRequiredElements); //4 days from due date considered as Escalation
        }
    }

    @Override
    public boolean updateOrdersById(Orders orders)
    {
        Orders existingOrder= ordersRepo.getReferenceById(orders.getOrderId());
        if(existingOrder == null)
        {
            return false;
        }
        ordersRepo.save(orders);
        return true;
    }
}
