package bliss.com.flight_operations_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FlightOperationsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightOperationsServiceApplication.class, args);
	}

}
