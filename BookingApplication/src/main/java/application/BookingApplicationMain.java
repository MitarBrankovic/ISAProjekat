package application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(scanBasePackages = {"application"})
@RestController
public class BookingApplicationMain {
	
	public static void main(String[] args) {
		SpringApplication.run(BookingApplicationMain.class, args);
		
		}
		
}
