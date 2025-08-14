package backend.e_commerce.representaion.request.payment;

import backend.e_commerce.application.command.payment.PaymentCancelledCommand;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class PaymentCancelRequest {
    private final UUID orderId;
    private final Long[] itemIds;

    private final String paymentKey;
    private final String cancelReason;
    private final int cancelAmount;

    public PaymentCancelledCommand toCommand() {
        return PaymentCancelledCommand.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .cancelReason(cancelReason)
                .itemIds(itemIds)
                .cancellationAmount(cancelAmount)
                .build();
    }
}