package com.figi.bks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BksApplication {

	public static void main(String[] args) {
		SpringApplication.run(BksApplication.class, args);
	}

}
