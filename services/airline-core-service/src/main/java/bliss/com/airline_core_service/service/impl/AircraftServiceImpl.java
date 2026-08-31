package bliss.com.airline_core_service.service.impl;

import bliss.com.airline_core_service.mapper.AircraftMapper;
import bliss.com.airline_core_service.mapper.AirlineMapper;
import bliss.com.airline_core_service.model.Aircraft;
import bliss.com.airline_core_service.model.Airline;
import bliss.com.airline_core_service.repository.AircraftRepository;
import bliss.com.airline_core_service.repository.AirlineRepository;
import bliss.com.airline_core_service.service.AircraftService;
import bliss.com.payload.request.AircraftRequest;
import bliss.com.payload.response.AircraftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;


    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () ->new Exception("airline does not exist for this ownerId")
                );
        Aircraft aircraft = AircraftMapper.toEntity(request, airline);

        if(aircraftRepository.existsByCode (aircraft.getCode())){
            throw new Exception("code already exist with another aircraft");
        }

        if (aircraft.getTotalSeats() > aircraft.getSeatingCapacity()) {
            throw new Exception("seating capacity can't exceed to total seat");
        }
        return AircraftMapper.toResponse(
                aircraftRepository.save(aircraft)
        );
    }


    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(
                        ()-> new Exception("Aircraft not exist with id")

                );
        return AircraftMapper.toResponse(aircraft);


    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        ()-> new Exception("this owner do not have airline")
                );
        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(AircraftMapper::toResponse).toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        ()-> new Exception("this owner do not have airline")
                );
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());
        if(aircraft == null) {
            throw new Exception("Aircraft not exist with id");
        }
        if(aircraft.getCode()!=null
                && !aircraft.getCode().equals(request.getCode())
                && aircraftRepository.existsByCode(request.getCode())){
            throw new Exception("code already exist with another aircraft");
        }
        AircraftMapper.updateEntity(aircraft, request);
        return AircraftMapper.toResponse(
                aircraftRepository.save(aircraft)
        );
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        ()-> new Exception("this owner do not have airline")
                );
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());
        if(aircraft == null) {
            throw new Exception("Aircraft not exist with id");
        }
        aircraftRepository.delete(aircraft);


    }
}
