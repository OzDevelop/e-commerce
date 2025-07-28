package backend.e_commerce.infrastructure.out.pg.toss.response;

import backend.e_commerce.domain.payment.PaymentLedger;
import backend.e_commerce.domain.payment.PaymentMethod;
import backend.e_commerce.domain.payment.PaymentStatus;
import backend.e_commerce.infrastructure.out.pg.toss.response.payment.method.Card;
import lombok.Getter;

@Getter
public class PaymentConfirmResponseDto {
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String paymentStatus;
    private String paymentMethod;
    private Card card;
    private int totalAmount; //총 결제 금액, 결제가 취소되는 등 상태 변경에도 최초 결제된 결제 금액으로 유지.
    private int balanceAmount;
    private int suppliedAmount; // 공급가액, 결제 취소 및 부분 취소가 발생하면 값이 변경됨.
    private String requestedAt;
    private String approvedAt;

    public PaymentLedger toPaymentLedgerEntity() {
        return PaymentLedger.builder()
                .paymentKey(paymentKey)
                .method(PaymentMethod.fromMethodName(paymentMethod))
                .paymentStatus(PaymentStatus.valueOf(paymentStatus))
                .totalAmount(totalAmount)
                .balanceAmount(balanceAmount)
                .build();


    }
}
