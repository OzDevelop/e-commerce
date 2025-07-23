package backend.e_commerce.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Payment {
    private final Long paymentId;
    private final String paymentKey;

    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    private int totalAmount;
    private int canceledAmount;

    public boolean isSuccess() {
        return paymentStatus == PaymentStatus.COMPLETED;
    }
}
