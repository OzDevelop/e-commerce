package backend.e_commerce.application.command.payment;

import backend.e_commerce.representaion.request.payment.PaymentConfirmRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentApprovedCommand {
    private final String paymentKey;
    private final String orderId;
    private final int amount;

    public PaymentConfirmRequestDto toPaymentConfirmRequestDto() {
        return new PaymentConfirmRequestDto(
                paymentKey,
                orderId,
                amount
        );
    }


}
