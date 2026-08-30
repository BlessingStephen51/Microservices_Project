package bliss.com.airline_core_service.repository;

import bliss.com.airline_core_service.model.Airline;
import bliss.com.enums.AirlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineRepository  extends JpaRepository<Airline, Long> {

    Optional<Airline> findByOwnerId(Long ownerId);

    List<Airline> findByStatus(AirlineStatus status);
}
