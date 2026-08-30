package bliss.com.flight_operations_service.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSchedule {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Flight flight;

    @Column(nullable = false)
    private Long departureAirportId;

    @Column(nullable = false)
    private Long arrivalAirportId;

    @Column(nullable = false)
    private LocalTime departureTime;

    @Column(nullable = false)
    private LocalTime arrivalTime;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;


    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> operatingDays;

    // 11/03/2026 to 10/04/2026

    // mon, tue, wed, thur

    private Boolean isActive = true;



}
