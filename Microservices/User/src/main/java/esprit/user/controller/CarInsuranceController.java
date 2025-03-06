package esprit.user.controller;

import esprit.user.models.CarInsurance;
import esprit.user.service.CarInsuranceService;
import esprit.user.service.EmailService;
import esprit.user.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carinsurance")
@RequiredArgsConstructor
public class CarInsuranceController {
    private final CarInsuranceService carInsuranceService;
    private final PaymentService paymentService;
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<CarInsurance> createCarInsurance(@RequestBody CarInsurance carInsurance) {
        return ResponseEntity.ok(carInsuranceService.createCarInsurance(carInsurance));
    }

    @GetMapping
    public ResponseEntity<List<CarInsurance>> getAllCarInsurances() {
        return ResponseEntity.ok(carInsuranceService.getAllCarInsurances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarInsurance> getCarInsuranceById(@PathVariable Long id) {
        return carInsuranceService.getCarInsuranceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarInsurance> updateCarInsurance(
            @PathVariable Long id,
            @RequestBody CarInsurance carInsurance
    ) {
        return ResponseEntity.ok(carInsuranceService.updateCarInsurance(id, carInsurance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarInsurance(@PathVariable Long id) {
        carInsuranceService.deleteCarInsurance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarInsurance>> advancedSearch(
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) CarInsurance.FuelType fuelType
    ) {
        return ResponseEntity.ok(
                carInsuranceService.advancedSearch(make, model, year, fuelType)
        );
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@PathVariable Long id) {
        try {
            CarInsurance carInsurance = carInsuranceService.getCarInsuranceById(id)
                    .orElseThrow(() -> new RuntimeException("Car Insurance not found"));

            // Get premium from additionalDetails
            double premium = Double.parseDouble(
                    carInsurance.getAdditionalDetails().get("premium_calculation")
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
        CarInsurance carInsurance = carInsuranceService.getCarInsuranceById(id)
                .orElseThrow(() -> new RuntimeException("Car Insurance not found"));

        double premium = Double.parseDouble(
                carInsurance.getAdditionalDetails().get("premium_calculation")
        );

        paymentService.handleSuccessfulPayment(
                premium,
                "Car Insurance Policy: " + carInsurance.getPolicyNumber()
        );

        emailService.sendEmail(
                "Benaissarihem036@gmail.com",
                "Car Insurance Payment Confirmation",
                String.format("""
                    Your payment of $%.2f for Car Insurance has been confirmed.
                    Policy Number: %s
                    Vehicle: %s %s (%d)
                    """,
                        premium,
                        carInsurance.getPolicyNumber(),
                        carInsurance.getMake(),
                        carInsurance.getModel(),
                        carInsurance.getYear()
                )
        );

        return ResponseEntity.ok().build();
    }
}