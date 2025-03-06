package esprit.user.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "insurance_company_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceCompanyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Company Basic Information
    @Column(length = 255)
    private String companyName;

    @Column(length = 100)
    private String industrySector;

    @Column(length = 50)
    private String taxRegistrationNumber;

    @Column(length = 500)
    private String headOfficeAddress;

    // Business Details
    @Column(precision = 10, scale = 2)
    private BigDecimal businessPremisesSize;

    private Integer numberOfEmployees;

    @Column(precision = 10, scale = 2)
    private BigDecimal valueOfProfessionalEquipment;

    // Insurance Specifics
    @ElementCollection
    @CollectionTable(
            name = "insurance_company_details",
            joinColumns = @JoinColumn(name = "company_insurance_id")
    )
    @MapKeyColumn(name = "detail_key")
    @Column(name = "detail_value")
    private Map<String, String> additionalDetails = new HashMap<>();

    @ElementCollection
    @CollectionTable(
            name = "insurance_company_coverage",
            joinColumns = @JoinColumn(name = "company_insurance_id")
    )
    @MapKeyColumn(name = "coverage_type")
    @Column(name = "is_selected")
    private Map<String, Boolean> insuranceOptions = new HashMap<>();

    @Enumerated(EnumType.STRING)
    private PremisesOccupationType premisesOccupationType;

    @Enumerated(EnumType.STRING)
    private InsuranceType typeOfInsuranceRequired;

    private Boolean presenceOfSecuritySystem;

    private LocalDateTime registrationDate;

    // Enums
    public enum PremisesOccupationType {
        OWNER,
        TENANT,
        LANDLORD
    }

    public enum InsuranceType {
        MULTI_RISK,
        LIABILITY,
        LEGAL_PROTECTION,
        BUSINESS_INTERRUPTION,
        PROPERTY_DAMAGE
    }

    // Utility methods
    public void addDetail(String key, String value) {
        additionalDetails.put(key, value);
    }

    public void addInsuranceOption(String type, Boolean selected) {
        insuranceOptions.put(type, selected);
    }

    @PrePersist
    protected void onCreate() {
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
    }
}