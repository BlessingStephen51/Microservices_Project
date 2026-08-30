package bliss.com.flight_operations_service.service;

import bliss.com.payload.request.FlightInstanceRequest;
import bliss.com.payload.request.FlightScheduleRequest;
import bliss.com.payload.response.FlightScheduleResponse;

import java.util.List;

public interface FlightScheduleService {

    FlightScheduleResponse createFlightSchedule(Long airlineId,
                                                FlightScheduleRequest request
    ) throws Exception;

    FlightScheduleResponse getFlightScheduleById(Long id) throws Exception;
    List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId);
    FlightScheduleResponse updateFlightSchedule(Long id,
                                                FlightScheduleRequest flightScheduleRequest) throws Exception;
    void deleteFlightSchedule(Long id) throws Exception;


}
