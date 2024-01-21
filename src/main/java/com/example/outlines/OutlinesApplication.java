package com.example.outlines;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.example.outlines.model")
@SpringBootApplication
public class OutlinesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutlinesApplication.class, args);
	}

}
