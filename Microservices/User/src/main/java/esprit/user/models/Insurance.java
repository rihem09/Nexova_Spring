package esprit.user.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mandatory Fields from Images
    @Column(nullable = false)
    private Double homeArea;

    @Column(nullable = false)
    private Integer numberOfRooms;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HomeType homeType;

    @Column(nullable = false)
    private Integer yearOfConstruction;

    @Column(nullable = false)
    private String fullAddress; // Adresse complète

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OccupancyType occupancyType; // Type d'occupation

    @Column(nullable = false)
    private Double insuredFurnitureValue; // Valeur du mobilier assuré

    @Column(nullable = false)
    private Boolean alarmSystem; // Présence d'un système d'alarme

    // Optional Insurance Guarantees
    @Column
    private Boolean antiTheftProtection; // Protection Anti-Vol

    @Column
    private Boolean glassProtection; // Protection des Glaces et Vitrages

    @Column
    private Boolean waterDamageProtection; // Dégâts des Eaux

    @Column
    private Boolean fireExplosionProtection; // Incendie et Explosion

    @Column
    private Boolean naturalDisasterProtection; // Catastrophes Naturelles

    @Column
    private Boolean civilLiabilityProtection; // Responsabilité Civile Locative

    @Column
    private Boolean emergencyAssistance; // Assistance 24/7

    @Column
    private Boolean newValueProtection; // Valeur à Neuf des Biens

    @Column
    private Boolean electricalDamageProtection; // Dommages Électriques

    // Insurance Type
    @Enumerated(EnumType.STRING)
    private InsuranceType insuranceType;

    // Metadata
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Additional Details as JSON
    @ElementCollection
    @CollectionTable(name = "insurance_details")
    @MapKeyColumn(name = "detail_key")
    @Column(name = "detail_value")
    private Map<String, String> additionalDetails = new HashMap<>();

    // Enums
    public enum HomeType {
        APARTMENT, DETACHED_HOUSE, VILLA, STUDIO
    }

    public enum OccupancyType {
        PRIMARY_RESIDENCE, SECONDARY_RESIDENCE, RENTAL
    }

    public enum InsuranceType {
        PROFESSIONAL, PARTICULAR
    }

    // Utility method to add additional details
    public void addDetail(String key, String value) {
        this.additionalDetails.put(key, value);
    }
}