package bliss.com.location_service.Service.impl;

import bliss.com.location_service.Controller.model.Airport;
import bliss.com.location_service.Controller.model.City;
import bliss.com.location_service.Service.AirportService;
import bliss.com.location_service.repository.AirportRepository;
import bliss.com.location_service.repository.CityRepository;
import bliss.com.location_service.mapper.AirportMapper;
import bliss.com.payload.request.AirportRequest;
import bliss.com.payload.response.AirportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest request) throws Exception {
        if(airportRepository.findByIataCode(request.getIataCode()).isPresent()){
            throw new Exception("Airport with Iata Code Already Exist");
        }

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new Exception("City not found"));
        Airport airport = AirportMapper.toEntity(request);

        airport.setCity(city);

        Airport savedAirport = airportRepository.save(airport);

        return AirportMapper.toResponse(savedAirport);
    }


    @Override
    public AirportResponse getAirportById(Long id) throws Exception{
        Airport airport = airportRepository.findById(id).orElseThrow(
                ()-> new Exception("airport not exist with provided id")
        );
        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {
        Airport existingAirport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("airport not exist with id " +id)
        );
        if(request.getIataCode()!= null
        && ! existingAirport.getIataCode().equals(request.getIataCode())
            && airportRepository.findByIataCode(request.getIataCode()).isPresent()
        ){
            throw new Exception("Airport with Iata Code Already Exist");

        }
        AirportMapper.updateEntity(request, existingAirport);
        Airport updatedAirport = airportRepository.save(existingAirport);
        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    public void deleteAirport(Long id) throws Exception {
        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("airport not exist with provided id")
        );
        airportRepository.delete(airport);


    }

    @Override
    public List<AirportResponse> getAllByCityId(Long cityId) {
        List<Airport> airports = airportRepository.getByCityId(cityId);
        return airports.stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}
