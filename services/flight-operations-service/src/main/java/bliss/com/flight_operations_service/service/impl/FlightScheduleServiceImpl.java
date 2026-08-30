package bliss.com.flight_operations_service.service.impl;

import bliss.com.enums.FlightStatus;
import bliss.com.flight_operations_service.mapper.FlightScheduleMapper;
import bliss.com.flight_operations_service.model.Flight;
import bliss.com.flight_operations_service.model.FlightSchedule;
import bliss.com.flight_operations_service.repository.FlightRepository;
import bliss.com.flight_operations_service.repository.FlightScheduleRepository;
import bliss.com.flight_operations_service.service.FlightInstanceService;
import bliss.com.flight_operations_service.service.FlightScheduleService;
import bliss.com.payload.request.FlightInstanceRequest;
import bliss.com.payload.request.FlightScheduleRequest;
import bliss.com.payload.response.AirportResponse;
import bliss.com.payload.response.FlightScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private final FlightRepository flightRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightInstanceService flightInstanceService;

    @Override
    public FlightScheduleResponse createFlightSchedule(
            Long airlineId,
            FlightScheduleRequest request) throws Exception {
   //   todo watch for airlineId
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(()-> new Exception("flight not found with given id"));


        if(request.getEndDate().isBefore(request.getStartDate())){
            throw new Exception("end date is before start date");
        }
        FlightSchedule flightSchedule = FlightScheduleMapper.toEntity(request, flight);
        FlightSchedule savedSchedule = flightScheduleRepository.save(flightSchedule);

        // create flight instance for saved scheduled
        // 11/03/2026 to 10/04/2026

        // mon, tue, wed, thu


        List<DayOfWeek> operatingDays = savedSchedule.getOperatingDays();
        LocalDate startDate = savedSchedule.getStartDate();
        LocalDate endDate = savedSchedule.getEndDate();

        FlightInstanceRequest flightInstanceRequest = FlightInstanceRequest
                .builder()
                .scheduleId(savedSchedule.getId())
                .flightId(flight.getId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureAirportId(flight.getDepartureAirportId())
                .status(FlightStatus.SCHEDULED )
                .build();

        for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {


            if(operatingDays.contains(date.getDayOfWeek())) {
                flightInstanceRequest.setDepartureDateTime(
                        LocalDateTime.of(date, savedSchedule.getDepartureTime())
                );
                flightInstanceRequest.setArrivalDateTime(
                        LocalDateTime.of(date, savedSchedule.getArrivalTime())

                );
                flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest);

            }

        }
        return convertToFlightScheduleResponse(savedSchedule);
    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new Exception("flight schedule not found with id")
        );

        return convertToFlightScheduleResponse(flightSchedule);
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirline(Long airlineId) {
   //   todo: watch airlineId
        List<FlightSchedule> schedules = flightScheduleRepository.findByFlightAirlineId(airlineId);
        return schedules.stream().map(
                this::convertToFlightScheduleResponse
        ).toList();
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest flightScheduleRequest) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new Exception("flight schedule not found with id")
        );
        FlightScheduleMapper.updateEntity(flightScheduleRequest, flightSchedule);
        FlightSchedule updatedSchedule = flightScheduleRepository.save(flightSchedule);

        return convertToFlightScheduleResponse(updatedSchedule);
    }

    @Override
    public void deleteFlightSchedule(Long id) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new Exception("flight schedule not found with id")
        );
        flightScheduleRepository.delete(flightSchedule);

    }


    private FlightScheduleResponse convertToFlightScheduleResponse(FlightSchedule flightSchedule) {
 //       todo: service to service communication

        AirportResponse departureAirport = AirportResponse.builder()
                .id(flightSchedule.getDepartureAirportId())
                .build();
        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flightSchedule.getArrivalAirportId())
                .build();
        return FlightScheduleMapper.toResponse(
                flightSchedule, arrivalAirport, departureAirport
        );


    }
}
