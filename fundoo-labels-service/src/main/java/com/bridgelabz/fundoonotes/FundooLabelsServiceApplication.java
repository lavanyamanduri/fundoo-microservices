package com.bridgelabz.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FundooLabelsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooLabelsServiceApplication.class, args);
	}

}
