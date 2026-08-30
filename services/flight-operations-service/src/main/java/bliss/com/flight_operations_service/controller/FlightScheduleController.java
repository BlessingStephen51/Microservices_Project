package bliss.com.flight_operations_service.controller;


import bliss.com.flight_operations_service.service.FlightScheduleService;
import bliss.com.payload.request.FlightScheduleRequest;
import bliss.com.payload.response.AirportResponse;
import bliss.com.payload.response.ApiResponse;
import bliss.com.payload.response.FlightScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-schedules")
public class FlightScheduleController {

    private final FlightScheduleService flightScheduleService;


    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Validated @RequestBody FlightScheduleRequest flightScheduleRequest) throws Exception {
        // todo: watch for airline id
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightScheduleService.createFlightSchedule(
                        airlineId, flightScheduleRequest
                ));

    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleById(id));

    }


    @GetMapping
    public ResponseEntity<?> getFlightSchedules(
            @RequestHeader("X-Airline-Id") Long airlineId
    ) {
        return ResponseEntity.ok(
                flightScheduleService.getFlightScheduleByAirline(airlineId));

    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long id,
            @Validated @RequestBody FlightScheduleRequest request) throws Exception {
        return ResponseEntity.ok(flightScheduleService.updateFlightSchedule(id, request));


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightSchedule(@PathVariable Long id) throws Exception {
        flightScheduleService.deleteFlightSchedule(id);
        ApiResponse apiResponse = new ApiResponse("schedule removed success");
        return ResponseEntity.ok(apiResponse);


    }
}