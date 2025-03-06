package esprit.user.service;

import esprit.user.models.CarInsurance;
import esprit.user.repository.CarInsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarInsuranceService {
    private final CarInsuranceRepository carInsuranceRepository;
    private final PaymentService paymentService;

    @Transactional
    public CarInsurance createCarInsurance(CarInsurance carInsurance) {
        validateCarInsurance(carInsurance);
        calculatePremium(carInsurance);
        carInsurance.setPolicyNumber(generatePolicyNumber());
        return carInsuranceRepository.save(carInsurance);
    }

    @Transactional(readOnly = true)
    public List<CarInsurance> getAllCarInsurances() {
        return carInsuranceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<CarInsurance> getCarInsuranceById(Long id) {
        return carInsuranceRepository.findById(id);
    }

    @Transactional
    public CarInsurance updateCarInsurance(Long id, CarInsurance updatedInsurance) {
        return carInsuranceRepository.findById(id)
                .map(existing -> {
                    updateCarInsuranceFields(existing, updatedInsurance);
                    calculatePremium(existing);
                    return carInsuranceRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Car Insurance not found"));
    }

    @Transactional
    public void deleteCarInsurance(Long id) {
        carInsuranceRepository.deleteById(id);
    }

    public List<CarInsurance> advancedSearch(
            String make,
            String model,
            Integer year,
            CarInsurance.FuelType fuelType
    ) {
        return carInsuranceRepository.advancedSearch(make, model, year, fuelType);
    }

    private void calculatePremium(CarInsurance insurance) {
        BigDecimal basePremium = new BigDecimal("500.00");

        // Age factor
        double ageFactor = calculateAgeFactor(insurance.getYear());

        // Power factor
        double powerFactor = calculatePowerFactor(insurance.getPower());

        // Coverage factor
        double coverageFactor = calculateCoverageFactor(insurance.getCoverages());

        // Calculate final premium
        BigDecimal finalPremium = basePremium
                .multiply(BigDecimal.valueOf(ageFactor))
                .multiply(BigDecimal.valueOf(powerFactor))
                .multiply(BigDecimal.valueOf(coverageFactor));

        insurance.setPremium(finalPremium);
        insurance.addDetail("premium_calculation", finalPremium.toString());
        insurance.addDetail("base_premium", basePremium.toString());
    }

    private double calculateAgeFactor(Integer year) {
        int age = LocalDateTime.now().getYear() - year;
        return age < 3 ? 1.5 :
                age < 5 ? 1.3 :
                        age < 10 ? 1.1 : 1.0;
    }

    private double calculatePowerFactor(Integer power) {
        return power < 100 ? 1.0 :
                power < 200 ? 1.2 :
                        power < 300 ? 1.4 : 1.6;
    }

    private double calculateCoverageFactor(Map<CarInsurance.CoverageType, Boolean> coverages) {
        double factor = 1.0;
        for (Map.Entry<CarInsurance.CoverageType, Boolean> coverage : coverages.entrySet()) {
            if (Boolean.TRUE.equals(coverage.getValue())) {
                factor += 0.1; // Add 10% for each coverage
            }
        }
        return factor;
    }

    private void validateCarInsurance(CarInsurance insurance) {
        if (insurance.getMake() == null || insurance.getMake().trim().isEmpty()) {
            throw new IllegalArgumentException("Make cannot be empty");
        }
        if (insurance.getModel() == null || insurance.getModel().trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        if (insurance.getYear() == null || insurance.getYear() < 1900) {
            throw new IllegalArgumentException("Invalid year");
        }
        if (insurance.getPower() == null || insurance.getPower() <= 0) {
            throw new IllegalArgumentException("Invalid power");
        }
    }

    private void updateCarInsuranceFields(CarInsurance existing, CarInsurance updated) {
        if (updated.getMake() != null) existing.setMake(updated.getMake());
        if (updated.getModel() != null) existing.setModel(updated.getModel());
        if (updated.getYear() != null) existing.setYear(updated.getYear());
        if (updated.getPower() != null) existing.setPower(updated.getPower());
        if (updated.getFuelType() != null) existing.setFuelType(updated.getFuelType());
        if (updated.getCoverages() != null) existing.setCoverages(updated.getCoverages());
        if (updated.getEndDate() != null) existing.setEndDate(updated.getEndDate());
        if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
    }

    private String generatePolicyNumber() {
        return "CAR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}