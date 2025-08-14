package backend.e_commerce.application.command.payment;

import backend.e_commerce.representaion.request.payment.PaymentCancelRequest;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCancelledCommand {
    private UUID orderId;
    private String paymentKey;
    private String cancelReason;

    private Long[] itemIds;
    private int cancellationAmount;

    public PaymentCancelRequest toPaymentCancelRequestDto() {
        return PaymentCancelRequest.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .cancelReason(cancelReason)
                .itemIds(itemIds)
                .cancelAmount(cancellationAmount)
                .build();
    }
}
