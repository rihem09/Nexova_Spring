package esprit.user.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "car_insurance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Vehicle Information
    private String make;
    private String model;
    private Integer year;
    private Integer power;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    // Coverage Details
    @ElementCollection
    @CollectionTable(
            name = "car_insurance_coverage",
            joinColumns = @JoinColumn(name = "insurance_id")
    )
    @MapKeyColumn(name = "coverage_type")
    @Column(name = "is_covered")
    private Map<CoverageType, Boolean> coverages = new HashMap<>();

    // Insurance Details
    @Column(precision = 10, scale = 2)
    private BigDecimal premium;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Column(length = 50)
    private String policyNumber;

    @Enumerated(EnumType.STRING)
    private InsuranceStatus status;

    // Additional Details
    @ElementCollection
    @CollectionTable(
            name = "car_insurance_details",
            joinColumns = @JoinColumn(name = "insurance_id")
    )
    @MapKeyColumn(name = "detail_key")
    @Column(name = "detail_value")
    private Map<String, String> additionalDetails = new HashMap<>();

    public enum FuelType {
        PETROL,
        DIESEL,
        ELECTRIC,
        HYBRID,
        LPG
    }

    public enum CoverageType {
        ANTI_THEFT,
        GLASS_AND_WINDOW,
        WATER_DAMAGE,
        FIRE_AND_EXPLOSION,
        NATURAL_DISASTERS,
        TENANT_LIABILITY,
        ASSISTANCE_24_7,
        NEW_FOR_OLD,
        ELECTRICAL_DAMAGE
    }

    public enum InsuranceStatus {
        PENDING,
        ACTIVE,
        EXPIRED,
        CANCELLED
    }

    // Utility methods
    public void addCoverage(CoverageType type, boolean covered) {
        coverages.put(type, covered);
    }

    public void addDetail(String key, String value) {
        additionalDetails.put(key, value);
    }

    @PrePersist
    protected void onCreate() {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        if (status == null) {
            status = InsuranceStatus.PENDING;
        }
    }
}