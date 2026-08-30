package bliss.com.flight_operations_service.service;


import bliss.com.payload.request.FlightInstanceRequest;
import bliss.com.payload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FlightInstanceService {

    FlightInstanceResponse createFlightInstance(
            Long airlineId,
            FlightInstanceRequest request
    ) throws Exception;

    FlightInstanceResponse updateFlightInstanceById(Long id, FlightInstanceRequest request) throws Exception;

    FlightInstanceResponse getFlightInstanceById(Long id) throws Exception;

    Page<FlightInstanceResponse> getByAirlineId(Long airline,
                                                Long departureAirportAirportId,
                                                Long arrivalAirportId,
                                                Long flightId  ,
                                                LocalDate onDate,
                                                Pageable pageable);

    FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception;
    void deleteFlightInstance(Long id) throws Exception;

}
