package bliss.com.location_service.mapper;

import bliss.com.location_service.Controller.model.Airport;
import bliss.com.location_service.mapper.CityMapper;
import bliss.com.payload.request.AirportRequest;
import bliss.com.payload.response.AirportResponse;

public class AirportMapper {

    public static Airport updatedEntity;

    public static Airport toEntity(AirportRequest request ){
        if(request == null){return null;}

        return Airport.builder()
                .iataCode(request.getIataCode())
                .name(request.getName())
//                .timeZone(request.getTimeZone())
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .build();
    }

    public static AirportResponse toResponse(Airport airport){
        if(airport == null){return null;}
        
        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(airport.getDetailedName())
//                .timeZone(airport.getTimeZone())
                .address(airport.getAddress())
                .city(CityMapper.toResponse(airport.getCity()))
                .geoCode(airport.getGeoCode())
                .build();
    }

    public static void updateEntity(AirportRequest request, Airport airport){
        if(request == null || airport == null){return;}

        if(request.getIataCode() != null) {
            airport.setIataCode(request.getIataCode());
        }
        if(request.getName() != null) {
            airport.setName(request.getName());
        }
        if(request.getAddress() != null) {
            airport.setAddress(request.getAddress());
        }
        if(request.getGeoCode() != null) {
            airport.setGeoCode(request.getGeoCode());
        }
    }
}

