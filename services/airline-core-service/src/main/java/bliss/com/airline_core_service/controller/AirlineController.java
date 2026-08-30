package bliss.com.airline_core_service.controller;

import bliss.com.airline_core_service.service.AirlineService;
import bliss.com.enums.AirlineStatus;
import bliss.com.payload.request.AirlineRequest;
import bliss.com.payload.response.AirlineDropdownItem;
import bliss.com.payload.response.AirlineResponse;
import bliss.com.payload.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<AirlineResponse> createAirline(
            @Validated @RequestBody AirlineRequest airlineRequest,
            @RequestHeader("X-User-Id") Long userId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                airlineService.createAirline(airlineRequest, userId)
        );
    }


    @GetMapping("/admin")
    public ResponseEntity<AirlineResponse> getAirlineByOwner(
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(airlineService.getAirlineByOwner(userId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getAirlineById(

            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.getAirlineById(id));
    }


    @GetMapping
    public ResponseEntity<Page<AirlineResponse>> getAirlines(Pageable pageable) {
        return ResponseEntity.ok(airlineService.getAirlines(pageable));

    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<AirlineDropdownItem>> getAirlinesForDropdown() {
        return ResponseEntity.ok(airlineService.getAirlineDropdown());
    }


    @PutMapping
    public ResponseEntity<AirlineResponse> updateAirline(
            @Validated @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(airlineService.updateAirline(request, userId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirline(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        airlineService.deleteAirline(id, userId);
        ApiResponse res = new ApiResponse("airline deleted successfully");
        return ResponseEntity.ok(
                res
        );
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<AirlineResponse> approveAirline(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(id, AirlineStatus.ACTIVE));

    }

    @PostMapping("/{id}/suspend")
    public ResponseEntity<AirlineResponse> suspendAirline(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(id, AirlineStatus.INACTIVE));

    }

    @PostMapping("/{id}/ban")
    public ResponseEntity<AirlineResponse> banAirline(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(id, AirlineStatus.BANNED));

    }









}
