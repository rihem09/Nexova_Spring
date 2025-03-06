package esprit.user.service;

import esprit.user.models.Insurance;
import esprit.user.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;

    @Transactional
    public Insurance createInsurance(Insurance insurance) {
        // Validate insurance before saving
        validateInsurance(insurance);

        // Calculate insurance premium dynamically
        calculatePremium(insurance);

        return insuranceRepository.save(insurance);
    }

    @Transactional(readOnly = true)
    public List<Insurance> getAllInsurances() {
        return insuranceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Insurance> getInsuranceById(Long id) {
        return insuranceRepository.findById(id);
    }

    @Transactional
    public Insurance updateInsurance(Long id, Insurance updatedInsurance) {
        return insuranceRepository.findById(id)
                .map(existingInsurance -> {
                    updateInsuranceFields(existingInsurance, updatedInsurance);
                    calculatePremium(existingInsurance);
                    return insuranceRepository.save(existingInsurance);
                })
                .orElseThrow(() -> new RuntimeException("Insurance not found"));
    }

    @Transactional
    public void deleteInsurance(Long id) {
        insuranceRepository.deleteById(id);
    }

    // Advanced search method
    public List<Insurance> advancedSearch(
            Insurance.HomeType homeType,
            Integer yearOfConstruction,
            Boolean alarmSystem
    ) {
        return insuranceRepository.advancedSearch(
                homeType, yearOfConstruction, alarmSystem
        );
    }

    // Premium calculation algorithm
    private void calculatePremium(Insurance insurance) {
        double basePremium = 100.0; // Base premium

        // Factors affecting premium
        double ageFactor = calculateAgeFactor(insurance.getYearOfConstruction());
        double alarmSystemDiscount = insurance.getAlarmSystem() ? 0.9 : 1.0;
        double homeTypeFactor = getHomeTypeFactor(insurance.getHomeType());
        double areeFactor = calculateAreaFactor(insurance.getHomeArea());

        // Complex premium calculation
        double premium = basePremium *
                ageFactor *
                alarmSystemDiscount *
                homeTypeFactor *
                areeFactor;

        // Add additional details about premium calculation
        insurance.addDetail("premium_calculation", String.format("%.2f", premium));
        insurance.addDetail("base_premium", String.format("%.2f", basePremium));
    }

    private double calculateAgeFactor(int constructionYear) {
        int currentYear = java.time.Year.now().getValue();
        int age = currentYear - constructionYear;
        return age < 10 ? 0.8 : (age < 30 ? 1.0 : 1.2);
    }

    private double getHomeTypeFactor(Insurance.HomeType homeType) {
        return switch(homeType) {
            case VILLA -> 1.3;
            case APARTMENT -> 1.0;
            case STUDIO -> 0.9;
            case DETACHED_HOUSE -> 1.2;
        };
    }

    private double calculateAreaFactor(double area) {
        return area < 50 ? 0.9 : (area > 200 ? 1.3 : 1.0);
    }

    private void validateInsurance(Insurance insurance) {
        // Add validation logic
        if (insurance.getHomeArea() <= 0) {
            throw new IllegalArgumentException("Home area must be positive");
        }
        if (insurance.getYearOfConstruction() < 1800) {
            throw new IllegalArgumentException("Invalid construction year");
        }
    }

    private void updateInsuranceFields(Insurance existingInsurance, Insurance updatedInsurance) {
        // Update only non-null fields
        if (updatedInsurance.getHomeArea() != null)
            existingInsurance.setHomeArea(updatedInsurance.getHomeArea());
        if (updatedInsurance.getNumberOfRooms() != null)
            existingInsurance.setNumberOfRooms(updatedInsurance.getNumberOfRooms());
        if (updatedInsurance.getYearOfConstruction() != null)
            existingInsurance.setYearOfConstruction(updatedInsurance.getYearOfConstruction());
        if (updatedInsurance.getAlarmSystem() != null)
            existingInsurance.setAlarmSystem(updatedInsurance.getAlarmSystem());
        if (updatedInsurance.getHomeType() != null)
            existingInsurance.setHomeType(updatedInsurance.getHomeType());

    }
}