package com.custom.Billing.Software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BillingSoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingSoftwareApplication.class, args);
	}

}
