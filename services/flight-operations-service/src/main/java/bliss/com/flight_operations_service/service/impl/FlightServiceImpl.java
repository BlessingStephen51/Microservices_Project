package bliss.com.flight_operations_service.service.impl;

import bliss.com.enums.FlightStatus;
import bliss.com.flight_operations_service.mapper.FlightMapper;
import bliss.com.flight_operations_service.model.Flight;
import bliss.com.flight_operations_service.repository.FlightRepository;
import bliss.com.flight_operations_service.service.FlightService;
import bliss.com.payload.request.FlightRequest;
import bliss.com.payload.response.AircraftResponse;
import bliss.com.payload.response.AirlineResponse;
import bliss.com.payload.response.AirportResponse;
import bliss.com.payload.response.FlightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;



    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception {
        if(flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())){
            throw new Exception("flight with already exist");
        }
        Flight flight = FlightMapper.toEntity(flightRequest);
        flight.setAirlineId(airlineId);
        Flight saved = flightRepository.save(flight);

        return convertToFlightResponse(saved);

    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(Long airlineId,
                                                    Long departureAirportId,
                                                    Long arrivalAirportId,
                                                    Pageable pageable
    ) {

        return flightRepository.findByAirlineId(airlineId,
                departureAirportId,
                arrivalAirportId,
                pageable).map(this::convertToFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) throws Exception {
        Flight flight = flightRepository.findById(id).orElseThrow(
                ()-> new Exception("Flight not found with id")
        );
        return convertToFlightResponse(flight);
    }

    // F-451 updated
    // f-451

    // F-450
    //F-453 depId

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) throws Exception {
        Flight existing = flightRepository.findById(id).orElseThrow(
                ()-> new Exception("flight not found with id")
        );
        if(flightRequest.getFlightNumber()!= null &&
            flightRepository.existsByFlightNumberAndIdNot(flightRequest.getFlightNumber(), id)){
            throw new Exception("flight with already exist");
        }
        FlightMapper.updatedEntity(flightRequest, existing);
        Flight updated = FlightMapper.toEntity(flightRequest);
        return convertToFlightResponse(updated);
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) throws Exception {
        Flight existing = flightRepository.findById(id).orElseThrow(
                ()-> new Exception("flight not found with id")
        );
        existing.setStatus(status);
        Flight updated = flightRepository.save(existing);
        return convertToFlightResponse(updated);
    }

    @Override
    public void deleteFlight(Long airlineId, Long id) throws Exception {
        Flight existing = flightRepository.findByAirlineIdAndId(airlineId,id).orElseThrow(
                ()-> new Exception("flight not found with id")
        );
        flightRepository.delete(existing);

    }

    public FlightResponse  convertToFlightResponse(Flight flight){
        AircraftResponse aircraft = AircraftResponse.builder()
                .id(flight.getAircraftId())
                .build();
        AirlineResponse airline = AirlineResponse.builder()
                .id(flight.getAirlineId())
                .build();
        AirportResponse departureAirport = AirportResponse.builder()
                .id(flight.getDepartureAirportId())
                .build();
        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flight.getArrivalAirportId())
                .build();

        return FlightMapper.toResponse(flight,
                aircraft,
                airline,
                departureAirport,
                arrivalAirport
                );
    }
}
