package backend.e_commerce.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 결제 상태의 흐름 제어
 * Order 와 1대1 관계를 유지하면서
 * 결제 시도, 완료, 실패, 취소 등등 결제와 관련된 도메인
 *
 * PG 사를 Toss Payment로 선택하여 수정 필요.
 */
@Getter
@AllArgsConstructor
@Builder
public class Payment {
    private final String paymentKey; // -> PG 사에서 발급하는 결제 키, PaymentLedger로 이동

    private final PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    private int totalAmount;
    private int canceledAmount;

    //결제 완료가 가능한 상태인지
    private boolean canBeCompleted() {
        return paymentStatus == PaymentStatus.PENDING;
    }

    public void markCompleted() {
        if(!canBeCompleted()) {
            throw new IllegalStateException("결제 완료를 할 수 있는 상태가 아닙니다.");
        }
        this.paymentStatus = PaymentStatus.COMPLETED;
    }

    private boolean canCancel(int amount) {
        return amount > 0 &&
                canceledAmount + amount <= totalAmount &&
                paymentStatus != PaymentStatus.CANCELLED;
    }

    // 결제 취소,
    public void cancel(int amount) {
        if (!canCancel(amount)) {
            throw new IllegalArgumentException("잘못된 취소 요청입니다.");
        }

        this.canceledAmount += amount;

        if (this.canceledAmount == this.totalAmount) {
            this.paymentStatus = PaymentStatus.CANCELLED;
        }
    }

    public boolean isSuccess() {
        return paymentStatus == PaymentStatus.COMPLETED;
    }
}
