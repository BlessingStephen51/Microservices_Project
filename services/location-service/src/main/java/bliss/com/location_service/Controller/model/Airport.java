package bliss.com.location_service.Controller.model;


import bliss.com.embeddable.Address;
import bliss.com.embeddable.GeoCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false, length = 3)
    private String iataCode;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;

    @Column(name = "time zone id", length = 50)
    private String timeZone;

    @ManyToOne
    @JsonIgnore
    private City city;

    @JsonIgnore
    @Transient
    public String getDetailedName(){
        if(city!=null && city.getCountryCode()!=null){
            return name.toUpperCase() + "/" + city.getCityCode();
        }
        return name.toUpperCase();

    }


}


