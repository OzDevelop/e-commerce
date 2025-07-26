package backend.e_commerce.representaion.request.payment;

import lombok.Getter;

@Getter
public class PaymentConfirmRequestDto {
    String paymentKey;
    String orderId;
    int amount;
}
