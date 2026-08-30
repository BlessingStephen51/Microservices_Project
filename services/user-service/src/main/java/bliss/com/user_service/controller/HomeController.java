package bliss.com.user_service.controller;


import bliss.com.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController() {
        ApiResponse apiResponse = new ApiResponse("hey everyone! I'm user service of airline system");
        return apiResponse;
    }
}
