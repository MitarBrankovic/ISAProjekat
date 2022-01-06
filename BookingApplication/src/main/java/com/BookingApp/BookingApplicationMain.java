package com.BookingApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(scanBasePackages = {"com.BookingApp"})
public class BookingApplicationMain {
	
	public static void main(String[] args) {
		SpringApplication.run(BookingApplicationMain.class, args);
		
		}
}
