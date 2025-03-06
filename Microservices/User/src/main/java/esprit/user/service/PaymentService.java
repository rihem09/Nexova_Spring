package esprit.user.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private final EmailService emailService;

    public String createPaymentIntent(double amount) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (long)(amount * 100)); // Stripe expects amount in cents
        params.put("currency", "usd");
        params.put("payment_method_types", List.of("card"));

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent.getClientSecret();
    }

    public void handleSuccessfulPayment(double amount, String insuranceDetails) {
        String email = "Benaissarihem036@gmail.com";
        String subject = "Insurance Payment Confirmation";
        String body = String.format("""
            Thank you for your payment of $%.2f
            
            Insurance Details:
            %s
            
            This is your payment confirmation.
            """, amount, insuranceDetails);

        emailService.sendEmail(email, subject, body);
    }
}