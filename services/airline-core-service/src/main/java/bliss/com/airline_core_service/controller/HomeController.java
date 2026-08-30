package bliss.com.airline_core_service.controller;


import bliss.com.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController() {
        ApiResponse apiResponse = new ApiResponse(
                "hey everyone, I'm airline core service & i will manage airlines, "
                        + "aircraft fleet, aircraft models, "  +
                        "and operational inventory for the airline system."
        );
        return apiResponse;
    }

}
