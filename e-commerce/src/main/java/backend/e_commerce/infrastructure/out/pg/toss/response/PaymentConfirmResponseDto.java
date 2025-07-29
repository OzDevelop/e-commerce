package backend.e_commerce.infrastructure.out.pg.toss.response;

import backend.e_commerce.domain.payment.PaymentLedger;
import backend.e_commerce.domain.payment.PaymentMethod;
import backend.e_commerce.domain.payment.PaymentStatus;
import backend.e_commerce.infrastructure.out.pg.toss.response.payment.method.Card;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentConfirmResponseDto {
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String status;
    private String method;
    private Card card;
    private int totalAmount; //총 결제 금액, 결제가 취소되는 등 상태 변경에도 최초 결제된 결제 금액으로 유지.
    private int balanceAmount;
    private int suppliedAmount; // 공급가액, 결제 취소 및 부분 취소가 발생하면 값이 변경됨.
    private String requestedAt;
    private String approvedAt;

    public PaymentLedger toPaymentLedgerDomain() {
        return PaymentLedger.builder()
                .paymentKey(paymentKey)
                .method(PaymentMethod.fromMethodName(method))
                .paymentStatus(PaymentStatus.valueOf(status))
                .totalAmount(totalAmount)
                .balanceAmount(balanceAmount)
                .build();


    }
}
