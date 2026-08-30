package bliss.com.location_service.repository;

import bliss.com.location_service.Controller.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    Optional<Airport> findByIataCode(String iataCode);

     List<Airport> getByCityId(Long cityId);


}
