package bliss.com.location_service.Controller;


import bliss.com.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @GetMapping()
    public ApiResponse HomeController(){

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Hello everyone in location service of airline microservices");
        return apiResponse;
    }
}
