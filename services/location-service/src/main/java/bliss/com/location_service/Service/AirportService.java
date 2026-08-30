package bliss.com.location_service.Service;

import bliss.com.payload.request.AirportRequest;
import bliss.com.payload.response.AirportResponse;
import org.springframework.data.annotation.LastModifiedBy;

import java.util.List;

public interface AirportService {
    AirportResponse createAirport(AirportRequest request) throws Exception;
    AirportResponse getAirportById(Long id) throws Exception;

    List<AirportResponse> getAllAirports();

    AirportResponse updateAirport(Long id, AirportRequest request) throws Exception;
    void deleteAirport(Long id) throws Exception;
    List<AirportResponse> getAllByCityId(Long cityId);


}
