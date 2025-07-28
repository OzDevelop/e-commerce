package backend.e_commerce.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PaymentLedger {
    private Long ledgerId;         // 정산 레코드 식별자 (DB에서 부여)
    private String paymentKey;     // 결제의 키값

    private PaymentMethod method;  // 결제 방식
    private PaymentStatus paymentStatus; // 상태: DONE, CANCELED, PARTIAL_CANCELED 등

    private int totalAmount;             // 결제 총액
    private int canceledAmount;          // 누적 취소 금액
    private int balanceAmount;           // 취소 후 남은 금액
    private int payoutAmount;            // 정산 요청 또는 완료 금액

    protected PaymentLedger() {
    }

    public boolean isCancellable(int cancellationAmount) {
        return balanceAmount >= cancellationAmount;
    }

}
