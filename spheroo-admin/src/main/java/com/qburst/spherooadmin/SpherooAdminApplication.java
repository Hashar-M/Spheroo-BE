package com.qburst.spherooadmin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class SpherooAdminApplication {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	public static void main(String[] args) {
		new SpringApplicationBuilder(SpherooAdminApplication.class)
				.run(args);
	}
	@Component
	class MyRunner implements CommandLineRunner {

		@Autowired
		private Environment environment;

		@Override
		public void run(String... args) throws Exception {
				log.info("Active profiles: " + Arrays.toString(environment.getActiveProfiles()));
		}
	}

}
