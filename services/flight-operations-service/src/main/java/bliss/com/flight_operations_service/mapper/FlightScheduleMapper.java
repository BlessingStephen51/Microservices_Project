package bliss.com.flight_operations_service.mapper;

import bliss.com.flight_operations_service.model.Flight;
import bliss.com.flight_operations_service.model.FlightSchedule;
import bliss.com.payload.request.FlightScheduleRequest;
import bliss.com.payload.response.AirportResponse;
import bliss.com.payload.response.FlightScheduleResponse;

import java.time.LocalTime;

public class FlightScheduleMapper {

    public static FlightSchedule toEntity(
            FlightScheduleRequest request,
            Flight flight
    ){
        if(request ==null || flight == null) return null;

        return FlightSchedule.builder()
                .flight(flight)
                .departureAirportId(flight.getDepartureAirportId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureTime(LocalTime.from(request.getDepartureTime()))
                .arrivalTime(LocalTime.from(request.getArrivalTime()))
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .operatingDays(request.getOperatingDays())
                .isActive(request.getIsActive()!=null?request.getIsActive():true)
                .build();

    }


    public static FlightScheduleResponse toResponse(FlightSchedule fs,
                                                    AirportResponse arrival,
                                                    AirportResponse departure) {
        if(fs == null) return null;
        return FlightScheduleResponse.builder()
                .id(fs.getId())
                .flightId(fs.getFlight()!= null ? fs.getFlight().getId() : null)
                .flightNumber(fs.getFlight() != null ? fs.getFlight().getFlightNumber() : null)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureTime(fs.getDepartureTime())
                .arrivalTime(fs.getArrivalTime())
                .startDate(fs.getStartDate())
                .endDate(fs.getEndDate())

                .operationDays(fs.getOperatingDays())
                .isActive(fs.getIsActive())
                .build();

    }


    public static void updateEntity(FlightScheduleRequest request, FlightSchedule existing) {
        if (request == null || existing == null) return;
        if (request.getDepartureTime() != null) existing.setDepartureTime(request.getDepartureTime());
        if (request.getArrivalTime() != null) existing.setArrivalTime(request.getArrivalTime());
        if (request.getStartDate() != null) existing.setStartDate(request.getStartDate());
        if (request.getEndDate()!= null) existing.setEndDate(request.getEndDate());
        if (request.getOperatingDays() != null)existing.setOperatingDays(request.getOperatingDays());
        if (request.getIsActive() != null) existing.setIsActive(request.getIsActive());

    }



}
