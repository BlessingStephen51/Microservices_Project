package bliss.com.flight_operations_service.controller;

import bliss.com.flight_operations_service.service.FlightInstanceService;
import bliss.com.payload.request.FlightInstanceRequest;
import bliss.com.payload.response.ApiResponse;
import bliss.com.payload.response.FlightInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-instances")
public class FlightInstanceController {

    private final FlightInstanceService flightInstanceService;


    @PostMapping
    public ResponseEntity<FlightInstanceResponse>  createFlightInstance(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Validated @RequestBody FlightInstanceRequest request) throws Exception{
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightInstanceService.createFlightInstance(airlineId, request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse>  getFlightInstanceById(
            @PathVariable Long id)  throws Exception {
        return ResponseEntity.ok(flightInstanceService.getFlightInstanceById(id));

    }


    @GetMapping()
    public ResponseEntity<Page<FlightInstanceResponse>> getByAirLineId(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) LocalDate onDate,
            Pageable pageable) {
        return ResponseEntity.ok(flightInstanceService.getByAirlineId(
                airlineId,
                departureAirportId,
                arrivalAirportId,
                flightId,
                onDate,
                pageable));
    }



    @PutMapping("/{id} ")
    public ResponseEntity<FlightInstanceResponse>  updateFlightInstance(
            @PathVariable Long id,
            @RequestBody FlightInstanceRequest request) throws Exception {
        return ResponseEntity.ok(flightInstanceService.updateFlightInstance(id, request));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightInstance(@PathVariable Long id) throws Exception {
        flightInstanceService.deleteFlightInstance(id);
        ApiResponse res = new ApiResponse("flight instance deleted");
        return ResponseEntity.ok(res);
    }





}
