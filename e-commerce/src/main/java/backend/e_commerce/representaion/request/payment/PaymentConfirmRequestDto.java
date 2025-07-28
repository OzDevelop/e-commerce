package backend.e_commerce.representaion.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentConfirmRequestDto {
    String paymentKey;
    String orderId;
    int amount;
}
