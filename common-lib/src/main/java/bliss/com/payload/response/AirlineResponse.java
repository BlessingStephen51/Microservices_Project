package bliss.com.payload.response;

import bliss.com.embeddable.Support;
import bliss.com.enums.AirlineStatus;
import bliss.com.payload.dto.UserDto;
import jakarta.persistence.Entity;
import lombok.*;
import java.time.Instant;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineResponse {

    private Long id;

    private String iataCode;
    private String icaoCode;

    private String name;
    private String alias;

    private String logoUrl;
    private String website;

    private AirlineStatus status;
    private String alliance;

    private Long ownerId;
    private UserDto owner;
    private Long updatedById;

    private Instant createdAt;
    private Instant updatedAt;

    private CityResponse headquartersCity;
    private Support support;


}
