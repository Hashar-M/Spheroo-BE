package com.qburst.spherooadmin;

import com.qburst.spherooadmin.supplier.Supplier;
import com.qburst.spherooadmin.supplier.SupplierRepository;
import com.qburst.spherooadmin.supplieruser.SupplierUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class SpherooAdminApplicationTests {
    @Autowired
    SupplierUserRepository supplierUserRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Test
    public void getSupplier(){
        Optional<Supplier> list=supplierRepository.findById(1L);
//        System.out.println(list.get);
    }
    @Test
    public void getUsers(){
        System.out.println(supplierUserRepository.findAll());
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
