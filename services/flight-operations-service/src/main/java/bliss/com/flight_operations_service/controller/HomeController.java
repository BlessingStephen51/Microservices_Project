package bliss.com.flight_operations_service.controller;


import bliss.com.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController() {
        ApiResponse apiResponse = new ApiResponse("Flight Operation Service manages flights, " +
                "Flight Schedules, and Flight Instances. " + "It represents the core operational flight lifecycle."
        );
        return apiResponse;
    }
}
