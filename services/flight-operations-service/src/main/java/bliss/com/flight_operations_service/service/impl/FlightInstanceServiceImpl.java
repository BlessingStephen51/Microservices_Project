package bliss.com.flight_operations_service.service.impl;

import bliss.com.flight_operations_service.mapper.FlightInstanceMapper;
import bliss.com.flight_operations_service.model.Flight;
import bliss.com.flight_operations_service.model.FlightInstance;
import bliss.com.flight_operations_service.repository.FlightInstanceRepository;
import bliss.com.flight_operations_service.repository.FlightRepository;
import bliss.com.flight_operations_service.service.FlightInstanceService;
import bliss.com.payload.request.FlightInstanceRequest;
import bliss.com.payload.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightRepository flightRepository;
    private final FlightInstanceRepository flightInstanceRepository;



    @Override
    public FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest request) throws Exception {

        // todo : watch airlineId


        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                () -> new Exception("Flight Not Found")
        );
        // todo : service to service communication
        AircraftResponse aircraft = AircraftResponse
                .builder()
                .id(1L)
                .totalSeats(90)
                .build();
        FlightInstance flightInstance = FlightInstanceMapper.toEntity(request, flight);
        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());

        FlightInstance saved = flightInstanceRepository.save(flightInstance);

        // todo : create seat instances

        return convertToFlightInstanceResponse(saved);

    }


    @Override
    public FlightInstanceResponse updateFlightInstanceById(Long id, FlightInstanceRequest request) throws Exception {
        FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(
                ()-> new Exception("flight instance not found")

        );
        FlightInstanceMapper.updateEntity(request, existing);
        return convertToFlightInstanceResponse(existing);
    }


    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) throws Exception {
        FlightInstance flightInstance = flightInstanceRepository.findById(id).orElseThrow(
                ()-> new Exception("flight instance not found with id " + id)
        );
        return convertToFlightInstanceResponse(flightInstance);

    }

    @Override
    public Page<FlightInstanceResponse> getByAirlineId(Long airlineId,
                                                       Long departureAirportId,
                                                       Long arrivalAirportId,
                                                       Long flightId,
                                                       LocalDate onDate, Pageable pageable) {

        // todo : watch airlineId
        LocalDateTime start = onDate!= null? onDate.atStartOfDay():null;
        LocalDateTime end = onDate!= null? onDate.plusDays(1).atStartOfDay():null;

        return flightInstanceRepository.findByAirlineId(
                airlineId, departureAirportId, arrivalAirportId, flightId, start, end, pageable
        ).map(this::convertToFlightInstanceResponse );

    }

    @Override
    public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception {
        FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(
                ()-> new Exception("flight instance not found")
        );
        FlightInstanceMapper.updateEntity(request, existing);
        return convertToFlightInstanceResponse(flightInstanceRepository.save(existing));
    }

    @Override
    public void deleteFlightInstance(Long id) throws Exception {
        FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(
                ()-> new Exception("flight instance not found")
        );
        flightInstanceRepository.delete(existing);

    }


    private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance) {
        // todo : service to service communication
        AirlineResponse airline = AirlineResponse.builder()
                .id(flightInstance.getAirlineId())
                .build();
        AirportResponse departureAirport = AirportResponse.builder()
                .id(flightInstance.getDepartureAirportId())
                .build();
        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flightInstance.getArrivalAirportId())
                .build();
        AircraftResponse aircraft = AircraftResponse.builder()
                .id(flightInstance.getFlight().getAircraftId())
                .build();


        return FlightInstanceMapper.toResponse(
                flightInstance,
                aircraft,
                airline,
                departureAirport,
                arrivalAirport

        );

    }
}
