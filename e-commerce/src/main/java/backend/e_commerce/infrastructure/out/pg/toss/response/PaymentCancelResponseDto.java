package backend.e_commerce.infrastructure.out.pg.toss.response;

import backend.e_commerce.domain.payment.PaymentLedger;
import backend.e_commerce.domain.payment.PaymentMethod;
import backend.e_commerce.domain.payment.PaymentStatus;
import backend.e_commerce.infrastructure.out.pg.toss.response.payment.method.Cancel;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCancelResponseDto {
    private List<Cancel> cancels;

    private String orderId;
    private String paymentKey;
    private String method;
    private String status;
    private int totalAmount;
    private int balanceAmount;

    public PaymentLedger toPaymentLedgerDomain() {
        return PaymentLedger.builder()
                .paymentKey(paymentKey)
                .method(PaymentMethod.fromMethodName(method))
                .paymentStatus(PaymentStatus.valueOf(status))
                .totalAmount(totalAmount)
                .balanceAmount(balanceAmount)
                .payoutAmount(totalAmount-balanceAmount)
                .build();
    }
}
