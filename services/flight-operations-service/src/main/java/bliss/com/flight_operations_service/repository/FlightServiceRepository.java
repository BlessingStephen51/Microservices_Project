package bliss.com.flight_operations_service.repository;

import bliss.com.flight_operations_service.model.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightServiceRepository extends JpaRepository<FlightSchedule, Long> {

    List<FlightSchedule> findByFlightAirlineId(Long airlineId);




}
