package com.qburst.spherooadmin;

import com.qburst.spherooadmin.supplier.Supplier;
import com.qburst.spherooadmin.supplier.SupplierRepository;
import com.qburst.spherooadmin.supplieruser.SupplierUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class SpherooAdminApplicationTests {
    @Autowired
    SupplierUserRepository supplierUserRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Test
    public void getSupplier(){
        Optional<Supplier> supplier=supplierRepository.findById(6L);
        System.out.println(supplier);
        //System.out.println(supplier.get());
        if(supplier.isPresent()){
            Supplier supplier1=supplier.get();
            System.out.println(supplier1);
            System.out.println(supplier1.getSupplierUsers().get(1).getSupplier());
        }
    }
    @Test
    public void getUsers(){
        System.out.println(supplierUserRepository.findAll());
    }
    @Test
    void pagination(){
        Pageable page= PageRequest.of(0,4);
        Slice<Supplier> suppliers=supplierRepository.findAll(page);
/*
        System.out.println(suppliers.forEach(););
*/
        //System.out.println(suppliers.n());

    }
	/*@Autowired
	private SupplierRepository supplierRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void contextLoads() {
		SupplierAddress supplierAddress=SupplierAddress.builder()
				.town("Iritty")
				.pinCode("670 706")
				.buildNo("190")
				.country("India")
				.district("Kannur")
				.build();
		SupplierUsers supplierUser= SupplierUsers.builder()
				.name("Akhilesh")
				.mobileNumber(906145403)
				.fixedLineNumber(996135751)
				.supplierUserEmail("akhileshvg@2000@gmail.com")
				.build();

	   *//*Category category=Category.builder()
			   .categoryIcon(null)
			   .categoryDescription("Electricity")
			   .categoryName("Electriccity")
			   .build();*//*
		Category category=categoryRepository.findById(1L);
		Supplier supplier=Supplier.builder()
				.supplierName("AB company")
				.tier(4)
				.rating(4)
				.supplierAddress(supplierAddress)
				.supplierUser(supplierUser)
				//.category(category)
				.build();
		supplier.setCategory(category);
		supplierRepository.save(supplier);
	}
	@Test
	public void getSupplierDeatails(){
		Optional<Supplier> supplier=supplierRepository.findById(3);
		System.out.println(supplier);
	}
	@Test
	public  void fromUser(){
		Optional<Supplier> supplier=supplierRepository.findBySupplierUserName("Akhilesh");
		System.out.println(supplier);
	}
	@Test
	void  getCategory (){
		*//*Optional<Supplier> supplier=supplierRepository.findById(1);*//*
		int a=supplierRepository.getSupplier(1);
		System.out.println(a);
	}
*/
}
