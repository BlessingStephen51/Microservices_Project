package bliss.com.embeddable;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class GeoCode {

    private Double latitude;
    private Double longitude;
}
