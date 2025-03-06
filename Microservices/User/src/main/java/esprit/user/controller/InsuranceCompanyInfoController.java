package esprit.user.controller;

import esprit.user.models.InsuranceCompanyInfo;
import esprit.user.service.EmailService;
import esprit.user.service.InsuranceCompanyInfoService;
import esprit.user.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companyinsurance")
@RequiredArgsConstructor
public class InsuranceCompanyInfoController {
    private final InsuranceCompanyInfoService insuranceCompanyInfoService;
    private final PaymentService paymentService;
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<InsuranceCompanyInfo> createCompanyInsurance(
            @RequestBody InsuranceCompanyInfo companyInfo) {
        return ResponseEntity.ok(insuranceCompanyInfoService.createCompanyInfo(companyInfo));
    }

    @GetMapping
    public ResponseEntity<List<InsuranceCompanyInfo>> getAllCompanyInsurances() {
        return ResponseEntity.ok(insuranceCompanyInfoService.getAllCompanyInfos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceCompanyInfo> getCompanyInsuranceById(@PathVariable Long id) {
        return insuranceCompanyInfoService.getCompanyInfoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceCompanyInfo> updateCompanyInsurance(
            @PathVariable Long id,
            @RequestBody InsuranceCompanyInfo companyInfo) {
        return ResponseEntity.ok(insuranceCompanyInfoService.updateCompanyInfo(id, companyInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyInsurance(@PathVariable Long id) {
        insuranceCompanyInfoService.deleteCompanyInfo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<InsuranceCompanyInfo>> advancedSearch(
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) Boolean hasSecuritySystem,
            @RequestParam(required = false) Integer minEmployees) {

        if (hasSecuritySystem != null && hasSecuritySystem) {
            return ResponseEntity.ok(insuranceCompanyInfoService.findCompaniesWithSecuritySystems());
        } else if (minEmployees != null) {
            return ResponseEntity.ok(insuranceCompanyInfoService.findCompaniesWithLargeWorkforce(minEmployees));
        } else if (sector != null) {
            return ResponseEntity.ok(insuranceCompanyInfoService.findCompaniesByIndustrySector(sector));
        }

        return ResponseEntity.ok(insuranceCompanyInfoService.getAllCompanyInfos());
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@PathVariable Long id) {
        try {
            InsuranceCompanyInfo companyInfo = insuranceCompanyInfoService.getCompanyInfoById(id)
                    .orElseThrow(() -> new RuntimeException("Company Insurance not found"));

            // Calculate premium based on company size and equipment value
            double premium = companyInfo.getValueOfProfessionalEquipment()
                    .multiply(BigDecimal.valueOf(0.02))
                    .add(BigDecimal.valueOf(companyInfo.getNumberOfEmployees() * 50))
                    .doubleValue();

            String clientSecret = paymentService.createPaymentIntent(premium);

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", clientSecret);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/payment/confirm")
    public ResponseEntity<Void> confirmPayment(@PathVariable Long id) {
        InsuranceCompanyInfo companyInfo = insuranceCompanyInfoService.getCompanyInfoById(id)
                .orElseThrow(() -> new RuntimeException("Company Insurance not found"));

        double premium = companyInfo.getValueOfProfessionalEquipment()
                .multiply(BigDecimal.valueOf(0.02))
                .add(BigDecimal.valueOf(companyInfo.getNumberOfEmployees() * 50))
                .doubleValue();

        paymentService.handleSuccessfulPayment(
                premium,
                "Company Insurance: " + companyInfo.getCompanyName()
        );

        emailService.sendEmail(
                "Benaissarihem036@gmail.com",
                "Company Insurance Payment Confirmation",
                String.format("""
                    Payment Confirmation for %s
                    
                    Amount: $%.2f
                    Tax Registration Number: %s
                    Industry Sector: %s
                    Number of Employees: %d
                    
                    Thank you for choosing our insurance services.
                    """,
                        companyInfo.getCompanyName(),
                        premium,
                        companyInfo.getTaxRegistrationNumber(),
                        companyInfo.getIndustrySector(),
                        companyInfo.getNumberOfEmployees()
                )
        );

        return ResponseEntity.ok().build();
    }
}