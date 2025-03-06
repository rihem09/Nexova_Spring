package esprit.user.controller;

import esprit.user.models.Insurance;
import esprit.user.service.EmailService;
import esprit.user.service.InsuranceService;
import esprit.user.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
public class InsuranceController {
    private final InsuranceService insuranceService;
    private final PaymentService paymentService;
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Insurance> createInsurance(@RequestBody Insurance insurance) {
        return ResponseEntity.ok(insuranceService.createInsurance(insurance));
    }

    @GetMapping
    public ResponseEntity<List<Insurance>> getAllInsurances() {
        return ResponseEntity.ok(insuranceService.getAllInsurances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insurance> getInsuranceById(@PathVariable Long id) {
        return insuranceService.getInsuranceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Insurance> updateInsurance(
            @PathVariable Long id,
            @RequestBody Insurance insurance
    ) {
        return ResponseEntity.ok(insuranceService.updateInsurance(id, insurance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Insurance>> advancedSearch(
            @RequestParam(required = false) Insurance.HomeType homeType,
            @RequestParam(required = false) Integer yearOfConstruction,
            @RequestParam(required = false) Boolean alarmSystem
    ) {
        return ResponseEntity.ok(
                insuranceService.advancedSearch(homeType, yearOfConstruction, alarmSystem)
        );
    }
    @PostMapping("/{id}/payment")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@PathVariable Long id) {
        try {
            Insurance insurance = insuranceService.getInsuranceById(id)
                    .orElseThrow(() -> new RuntimeException("Insurance not found"));

            // Get calculated premium from additionalDetails
            double premium = Double.parseDouble(
                    insurance.getAdditionalDetails().get("premium_calculation")
            );

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
        Insurance insurance = insuranceService.getInsuranceById(id)
                .orElseThrow(() -> new RuntimeException("Insurance not found"));

        double premium = Double.parseDouble(
                insurance.getAdditionalDetails().get("premium_calculation")
        );

        paymentService.handleSuccessfulPayment(
                premium,
                insurance.toString()
        );

        emailService.sendEmail(
                "Benaissarihem036@gmail.com",
                "Payment Confirmation",
                "Your payment has been confirmed successfully"
        );

        return ResponseEntity.ok().build();
    }
}