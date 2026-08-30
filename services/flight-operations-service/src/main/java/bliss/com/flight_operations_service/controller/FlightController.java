package bliss.com.flight_operations_service.controller;

import bliss.com.enums.FlightStatus;
import bliss.com.flight_operations_service.service.FlightService;
import bliss.com.payload.request.FlightRequest;
import bliss.com.payload.response.FlightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;


    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @Validated @RequestBody FlightRequest flightRequest,
            @RequestHeader("Airline-Id") Long airlineId

    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightService.createFlight(airlineId, flightRequest));

    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightsById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightService.getFlightById(id));

    }

    @GetMapping("/airline")
    public ResponseEntity<Page<FlightResponse>> getFlightsByAirline(
            @RequestHeader("Airline-Id") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            Pageable pageable) {
        return ResponseEntity.ok(flightService.getFlightsByAirline(
                airlineId,
                departureAirportId,
                arrivalAirportId,
                pageable
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable Long id,
            @RequestBody FlightRequest request) throws Exception {
        return ResponseEntity.ok(flightService.updateFlight(id, request));
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<FlightResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam FlightStatus status) throws Exception {
        return ResponseEntity.ok(flightService.changeStatus(id, status));


    }


    @DeleteMapping("/{id}")
    public ResponseEntity<FlightResponse> deleteFlight(
            @PathVariable Long id,
            @RequestHeader("Airline-Id") Long airlineId) throws Exception {
        flightService.deleteFlight(airlineId, id);
        return ResponseEntity.noContent().build();



}
    }