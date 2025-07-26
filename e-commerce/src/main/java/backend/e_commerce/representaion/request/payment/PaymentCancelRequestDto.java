package backend.e_commerce.representaion.request.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentCancelRequestDto {
    private final String cancelReason;
    private final int cancelAmount;
}