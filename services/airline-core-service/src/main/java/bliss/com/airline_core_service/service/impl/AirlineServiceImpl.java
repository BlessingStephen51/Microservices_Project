package bliss.com.airline_core_service.service.impl;

import bliss.com.airline_core_service.repository.AirlineRepository;
import bliss.com.airline_core_service.mapper.AirlineMapper;
import bliss.com.airline_core_service.model.Airline;
import bliss.com.airline_core_service.service.AirlineService;
import bliss.com.enums.AirlineStatus;
import bliss.com.payload.request.AirlineRequest;
import bliss.com.payload.response.AirlineDropdownItem;
import bliss.com.payload.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;


    @Override
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) {

        Airline airline = AirlineMapper.toEntity(request, ownerId);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        ()-> new Exception("airline not found with ownerId " +ownerId)
                );
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineById(Long id) throws Exception {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(
                        ()-> new Exception("airline not found with ownerId " + id)
                );
        return AirlineMapper.toResponse(airline);

    }

    @Override
    public Page<AirlineResponse> getAirlines(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(
                AirlineMapper::toResponse
        );
    }


    @Override
    public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        ()-> new Exception("airline not found with ownerId " + ownerId)
                );
        AirlineMapper.updateEntity(airline, request);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        ()-> new Exception("airline not found with ownerId " + ownerId)
                );
        airlineRepository.delete(airline);

    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception {
        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(
                        ()-> new Exception("airline not found with ownerId " + airlineId)
                );
        airline.setStatus(status);
        Airline updatedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(updatedAirline);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {
        return airlineRepository.findByStatus(AirlineStatus.ACTIVE)
                .stream()
                .map(a ->AirlineDropdownItem.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .iataCode(a.getIataCode())
                        .icaoCode(a.getIcaoCode())
                        .logoUrl(a.getLogoUrl())
                        .build()).toList();
    }
}
