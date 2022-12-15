package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.orderDetails.AssignedOrderRepository;
import com.qburst.spherooadmin.orderDetails.Orders;
import com.qburst.spherooadmin.orderDetails.OrdersRepository;
import com.qburst.spherooadmin.service.ServiceRepository;
import com.qburst.spherooadmin.supplieruser.SupplierUser;
import com.qburst.spherooadmin.supplieruser.SupplierUserType;
import com.qburst.spherooadmin.supplieruser.SupplierUsersAddDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 * Service layer provides services related to supplier model.
 */
@Service
@AllArgsConstructor
public class SupplierServiceImp implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private ServiceRepository serviceRepository;
    private OrdersRepository ordersRepository;
    private AssignedOrderRepository assignedOrderRepository;

    /**
     * Method is used for persist a new supplier.
     * @param supplierAddDTO
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void addSupplier(SupplierAddDTO supplierAddDTO) throws EntityNotFoundException{
        /**
         * Creates a new supplier address from the {@link SupplierAddressAddDTO} in {@link SupplierAddDTO}
         */
        SupplierAddress supplierAddress =new SupplierAddress();
        supplierAddress.setDistrict(supplierAddDTO.getSupplierAddressAddDTO().getDistrict());
        supplierAddress.setTown(supplierAddDTO.getSupplierAddressAddDTO().getTown());
        supplierAddress.setCountry(supplierAddDTO.getSupplierAddressAddDTO().getCountry());
        supplierAddress.setBuildNo(supplierAddDTO.getSupplierAddressAddDTO().getBuildNo());
        supplierAddress.setPinCode(supplierAddDTO.getSupplierAddressAddDTO().getPinCode());

        /**
         * Creates a list of Supplier users from the list of {@link SupplierUsersAddDTO} in {@link SupplierAddDTO}
         */
        List<SupplierUsersAddDTO> supplierUsersAddDTOS = supplierAddDTO.getSupplierUsersAddDTOS();
        List<SupplierUser> supplierUsers=new ArrayList<>();

        supplierUsersAddDTOS.forEach(supplierUsersPostDTO->{
            SupplierUser supplierUser=new SupplierUser();
            supplierUser.setName(supplierUsersPostDTO.getSupplierUserName());
            supplierUser.setMobileNumber(supplierUsersPostDTO.getSupplierUserMobileNumber());
            supplierUser.setFixedLineNumber(supplierUsersPostDTO.getSupplierUserFixedMobileNumber());
            supplierUser.setSupplierUserEmail(supplierUsersPostDTO.getSupplierUserEmailId());
            supplierUser.setSupplierUserType(supplierUsersPostDTO.getSupplierUserType());
            supplierUsers.add(supplierUser);
        });

        /**
         * Below code creates a new Supplier from supplier address and list of supplier users created above, which corresponds to the requested supplier to persist.
         */
        Supplier supplier=new Supplier();

        supplier.setSupplierName(supplierAddDTO.getSupplierName());
        supplier.setRating(supplierAddDTO.getRating());
        supplier.setCategoryNames(supplierAddDTO.getCategoryNames());
        supplier.setSupplierAddress(supplierAddress);
        supplier.setSupplierUsers(supplierUsers);
        String categoryName=supplier.getCategoryNames();

        /**
         * Below code save the supplier if the Category for which supplier need to save exists.
         */
        if (categoryRepository.existsByCategoryName(categoryName)){
            supplier.setCategoryId(categoryRepository.getCategoryIdFromCategoryName(categoryName));
            supplier.getSupplierUsers().forEach(supplierUser -> supplierUser.setSupplier(supplier));
            supplierRepository.save(supplier);
        }
        /**
         * {@code throw new EntityNotFoundException();} is thrown while no category{@link com.qburst.spherooadmin.category.Category} exists under which a new supplier is need to save.
         */
        else{
             throw new EntityNotFoundException();
        }
    }

    /**
     * Method that gives a list of supplier.
     * @param pageNo
     * @param pageSize
     * @return a list of supplier as per the specified page number and size.
     */
    @Transactional
    public SupplierPageDTO getPageOfSupplier(int pageNo, int pageSize){
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Supplier> suppliersPage=supplierRepository.findAll(pageable);
        List<SupplierGetDTO> supplierGetDTOList=new ArrayList<>();
        suppliersPage.forEach(supplier -> {
            SupplierGetDTO supplierGetDTO=new SupplierGetDTO();
                supplierGetDTO.setSupplierId(supplier.getSupplierId());
                supplierGetDTO.setSupplierName(supplier.getSupplierName());
                supplierGetDTO.setCategory(supplier.getCategoryNames());
                supplierGetDTO.setPinCode(supplier.getSupplierAddress().getPinCode());
                supplier.getSupplierUsers().forEach(supplierUser -> {
                    if (supplierUser.getSupplierUserType() == SupplierUserType.MANAGER) {
                        supplierGetDTO.setContactName(supplierUser.getName());
                        supplierGetDTO.setContactNumber(supplierUser.getMobileNumber());
                        supplierGetDTO.setEmailId(supplierUser.getSupplierUserEmail());
                    }
                });
                supplierGetDTOList.add(supplierGetDTO);
                        });
        SupplierPageDTO supplierPageDTO = new SupplierPageDTO();
        supplierPageDTO.setSupplierList(supplierGetDTOList);
        supplierPageDTO.setPageSize(suppliersPage.getSize());
        supplierPageDTO.setPageNumber(suppliersPage.getNumber());
        supplierPageDTO.setTotalPages(suppliersPage.getTotalPages());
        return supplierPageDTO;
    }

    /**
     * Deletes a supplier from supplier name.
     * @param supplierName
     * @return true if delete operation is success.
     */
    @Transactional
    public boolean deleteSupplierFromSupplierName(String supplierName){
        if(supplierRepository.existsBySupplierName(supplierName)){
            long supplierId=supplierRepository.getSupplierIdFromSupplierName(supplierName);
            supplierRepository.deleteById(supplierId);
            //supplierRepository.deleteBySupplierName(supplierName);
            return true;
        }
        return false;
    }

    /**
     * Method for find a supplier from supplier name.
     * @param supplierName
     * @return supplier of given supplier name.
     */
    public Optional<Supplier> getTheSupplier(String supplierName){
        if(supplierRepository.existsBySupplierName(supplierName)){
            long supplierId=supplierRepository.getSupplierIdFromSupplierName(supplierName);
            Optional<Supplier> supplier=supplierRepository.findById(supplierId);
            return supplier;
        }
        return Optional.empty();
    }

    /**
     * Update an existing supplier of corresponding ID from a given supplier instance.
     * @param supplier
     * @return true for successful updation.
     */
    @Transactional
    public boolean editTheSupplier(Supplier supplier){
        if(supplier.getSupplierId()>=0 && supplierRepository.existsById(supplier.getSupplierId())){
            supplierRepository.save(supplier);
            return true;
        }
        return false;
    }

    /**
     * function for returning supplier list with given categories.
     * @param orderId accepts order id.
     * @return list of supplier data which matches criteria.
     */
    @Override
    public List<SupplierToAssignDTO> getSuppliersToAssign(long orderId) {
        Orders orders=ordersRepository.getReferenceById(orderId);
        long categoryId=orders.getCategoryId();
        String zipcode=orders.getZipCode();
        List<Supplier>  supplierList = supplierRepository.findByCategoryId(categoryId,Integer.parseInt(zipcode));
        List<SupplierToAssignDTO> supplierToAssignDTOList = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            SupplierToAssignDTO supplierToAssignDTO = new SupplierToAssignDTO();
            supplierToAssignDTO.setSupplierId(supplier.getSupplierId());
            supplierToAssignDTO.setSupplierName(supplier.getSupplierName());
            supplierToAssignDTO.setCategoryName(supplier.getCategoryNames());
            supplierToAssignDTO.setServiceName(serviceRepository.getReferenceById(orders.getServiceId()).getServiceName());
            supplierToAssignDTO.setRating(supplier.getRating());
            supplierToAssignDTO.setAssignedTickets(assignedOrderRepository.getAssignedOrderCount(supplier.getSupplierId()));
            supplierToAssignDTOList.add(supplierToAssignDTO);
        }
        return supplierToAssignDTOList;
    }

    /**
     * The method map the {@link Supplier} data  for the below filtering parameter into {@link FilterSupplierForAssignDTO}.
     * @param categoryName
     * @param pin
     * @param rating
     * @return {@link Page<FilterSupplierForAssignDTO>}
     */
    public List<FilterSupplierForAssignDTO> filteredListOfSupplierForACategoryId(String categoryName, String pin, int rating){

            List<FilterSupplierForAssignDTO> page = supplierRepository.findAllOrderBySupplierName(categoryName, rating, pin);
            /**
             * value for assigned orders for each {@link Supplier} is taken from {@link AssignedOrderRepository}
             * and added to {@link FilterSupplierForAssignDTO}.
             */
            page.forEach(filterSupplierForAssignDTO -> {
                filterSupplierForAssignDTO.setAssignedTickets(assignedOrderRepository.getAssignedOrderCount(filterSupplierForAssignDTO.getSupplierId()));
            });
            return page;
        }
}
