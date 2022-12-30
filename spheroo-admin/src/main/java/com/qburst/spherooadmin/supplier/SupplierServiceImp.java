package com.qburst.spherooadmin.supplier;

import com.qburst.spherooadmin.category.CategoryRepository;
import com.qburst.spherooadmin.exception.CategoryNotFoundException;
import com.qburst.spherooadmin.exception.SupplierNameConstraintException;
import com.qburst.spherooadmin.exception.SupplierNotFoundException;
import com.qburst.spherooadmin.orderDetails.AssignedOrderRepository;
import com.qburst.spherooadmin.orderDetails.Orders;
import com.qburst.spherooadmin.orderDetails.OrdersRepository;
import com.qburst.spherooadmin.search.SupplierPaginationFilter;
import com.qburst.spherooadmin.service.ServiceRepository;
import com.qburst.spherooadmin.signup.ResponseDTO;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.qburst.spherooadmin.constants.CategoryConstants.CATEGORY_NOT_FOUND;
import static com.qburst.spherooadmin.constants.ResponseConstants.SUPPLIER_NAME_DUPLICATE_VALUE;
import static com.qburst.spherooadmin.constants.SpherooConstants.SOMETHING_WENT_WRONG;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.ALREADY_IN_REQUESTED_STATE;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.SUPPLIER_NOT_FOUND;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.SUPPLIER_VISIBILITY_UPDATION_FAILURE;
import static com.qburst.spherooadmin.constants.SupplierModelConstants.SUPPLIER_VISIBILITY_UPDATION_MESSAGE;

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
    private OrdersRepository ordersRepository;
    private AssignedOrderRepository assignedOrderRepository;

    /**
     * Method is used for persist a new supplier.
     * @param supplierAddDTO
     * @throws CategoryNotFoundException
     */
    @Override
    @Transactional
    public void addSupplier(SupplierAddDTO supplierAddDTO){
        /**
         * Creates a new supplier address from the {@link SupplierAddressAddDTO} in {@link SupplierAddDTO}
         */
        if (supplierRepository.existsBySupplierName(supplierAddDTO.getSupplierName()))
            throw new SupplierNameConstraintException(SUPPLIER_NAME_DUPLICATE_VALUE);
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
         * {@code throw new CategoryNotFoundException(CATEGORY_NOT_FOUND);} is thrown while no category{@link com.qburst.spherooadmin.category.Category} exists under which a new supplier is need to save.
         */
        else{
             throw new CategoryNotFoundException(CATEGORY_NOT_FOUND);
        }
    }

    /**
     * Method that gives a list of supplier as page based on some criteria {@link SupplierPagingConstraint}
     * @param pageNo
     * @param pageSize
     * @return a Page of supplier as per the specified page number and size.
     */
    @Transactional
    public SupplierPageDTO getPageOfSupplier(int pageNo, int pageSize, SupplierPaginationFilter specification){
        Pageable pageable;
        Page<Supplier> suppliersPage;
        if (!specification.getAsc()&& specification.getKey()!=SupplierPagingConstraint.name){
            pageable= PageRequest.of(pageNo,pageSize,Sort.by(specification.getKey().toString()).descending());
        }
        else if (specification.getAsc()&&specification.getKey()!=SupplierPagingConstraint.name){
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(specification.getKey().toString()).ascending());
        }
        else
            pageable=PageRequest.of(pageNo,pageSize);
        suppliersPage=supplierRepository.findAll(specification,pageable);
        List<SupplierGetDTO> supplierGetDTOList=new ArrayList<>();
        suppliersPage.forEach(supplier -> {
            SupplierGetDTO supplierGetDTO=new SupplierGetDTO();
                supplierGetDTO.setSupplierId(supplier.getSupplierId());
                supplierGetDTO.setSupplierName(supplier.getSupplierName());
                supplierGetDTO.setCategory(supplier.getCategoryNames());
                supplierGetDTO.setPinCode(supplier.getSupplierAddress().getPinCode());
                supplierGetDTO.setVisibility(supplier.isVisibility());
                supplier.getSupplierUsers().forEach(supplierUser -> {
                    if (supplierUser.getSupplierUserType() == SupplierUserType.MANAGER) {
                        supplierGetDTO.setContactName(supplierUser.getName());
                        supplierGetDTO.setContactNumber(supplierUser.getMobileNumber());
                        supplierGetDTO.setEmailId(supplierUser.getSupplierUserEmail());
                    }
                });
                supplierGetDTOList.add(supplierGetDTO);
                        });
        /**
         * below if else condition sort supplierGetDTOList based on contactName in {@link SupplierGetDTO}.
         */
        if (specification.getKey()==SupplierPagingConstraint.name&&!specification.getAsc()) {
            supplierGetDTOList.sort(Comparator.comparing(SupplierGetDTO::getContactName,String::compareToIgnoreCase).reversed());
        }
        else if (specification.getKey() == SupplierPagingConstraint.name){
            supplierGetDTOList.sort(Comparator.comparing(SupplierGetDTO::getContactName,String::compareToIgnoreCase));
        }

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
    public List<FilterSupplierForAssignDTO> getSuppliersToAssign(long orderId) {
        Orders orders=ordersRepository.getReferenceById(orderId);
        long categoryId=orders.getCategoryId();
        String zipcode=orders.getZipCode();
        List<Supplier>  supplierList = supplierRepository.findByCategoryId(categoryId,zipcode);
        List<FilterSupplierForAssignDTO> filterSupplierForAssignDTOList = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            FilterSupplierForAssignDTO filterSupplierForAssignDTO = new FilterSupplierForAssignDTO();
            filterSupplierForAssignDTO.setSupplierId(supplier.getSupplierId());
            filterSupplierForAssignDTO.setSupplierName(supplier.getSupplierName());
            filterSupplierForAssignDTO.setRating(supplier.getRating());
            filterSupplierForAssignDTO.setAssignedTickets(assignedOrderRepository.getAssignedOrderCount(supplier.getSupplierId()));
            filterSupplierForAssignDTOList.add(filterSupplierForAssignDTO);
        }
        return filterSupplierForAssignDTOList;
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

    /**
     * method for enable and disable a {@link Supplier}.
     * @param supplierId id value of supplier
     * @param action
     * @return {@link ResponseDTO} with true boolean value only for successful update.
     */
    @Override
    public ResponseDTO alterVisibilityOfSupplier(long supplierId, boolean action) {
        ResponseDTO responseDTO=new ResponseDTO();
        if(!supplierRepository.existsById(supplierId)){
            throw new  SupplierNotFoundException(SUPPLIER_NOT_FOUND);
        }
        else if(supplierRepository.findVisibilityById(supplierId)==action){
            responseDTO.setMessage(ALREADY_IN_REQUESTED_STATE);
            responseDTO.setSuccess(false);
            return responseDTO;
        }
        else if(supplierRepository.findVisibilityById(supplierId)==!action){
            supplierRepository.updateVisibilityById(action,supplierId);
            if(supplierRepository.findVisibilityById(supplierId)==action){
                responseDTO.setSuccess(true);
                responseDTO.setMessage(SUPPLIER_VISIBILITY_UPDATION_MESSAGE);
                return responseDTO;
            }
            responseDTO.setMessage(SUPPLIER_VISIBILITY_UPDATION_FAILURE);
            responseDTO.setSuccess(false);
            return responseDTO;
        }
        responseDTO.setMessage(SOMETHING_WENT_WRONG);
        return responseDTO;
    }
}
