package bliss.com.location_service.Service.impl;


import bliss.com.location_service.Controller.model.City;
import bliss.com.location_service.Service.CityService;
import bliss.com.location_service.mapper.CityMapper;
import bliss.com.location_service.repository.CityRepository;
import bliss.com.payload.request.CityRequest;
import bliss.com.payload.response.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

   @Override
   public CityResponse createCity(CityRequest request) throws Exception {

       if(cityRepository.existsByCityCode(request.getCityCode())){
           throw new Exception("City with given code already exist");
       }

       City city = CityMapper.toEntity(request);
       City result = cityRepository.save(city);
       return CityMapper.toResponse(result);
   }

   @Override
   public CityResponse getCityById(Long id) throws Exception {
       City city = cityRepository.findById(id).orElseThrow(
               ()-> new Exception("city not exit with given id")
       );
        return CityMapper.toResponse(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest request) throws Exception {
       City city = cityRepository.findById(id).orElseThrow(
               ()-> new Exception("city not exit with given id")
       );

       if(cityRepository.existsByCityCodeAndIdNot(request.getCityCode(), id)){
           throw new Exception("city with given code already exist");

       }

       CityMapper.updateEntity(city, request);
       City updatedCity = cityRepository.save(city);

        return CityMapper.toResponse(updatedCity);
    }

    @Override
    public void deleteCity(Long id) throws Exception{
            City city = cityRepository.findById(id).orElseThrow(
                    ()-> new Exception("city not exit with given id")
            );
            cityRepository.delete(city);


        }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRepository.searchByKeyword(keyword, pageable)
                .map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> getCityByCountryCode(String countryCode, Pageable pageable) {
        return null;
    }

    @Override
    public boolean cityExists(String cityCode) {
        return false;
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String upperCase, int page) {
        return null;
    }


}
