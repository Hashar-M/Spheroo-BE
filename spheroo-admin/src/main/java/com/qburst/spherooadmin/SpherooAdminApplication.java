package com.qburst.spherooadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpherooAdminApplication {
	/*@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
	public static void main(String[] args) {
		SpringApplication.run(SpherooAdminApplication.class, args);
	}

}
