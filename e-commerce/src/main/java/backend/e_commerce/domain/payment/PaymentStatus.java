package backend.e_commerce.domain.payment;

import java.util.Arrays;

public enum PaymentStatus {
    PENDING("결제 대기중"),
    COMPLETED("결제 완료"),
    FAILED("결제 실패"),
    CANCELLED("결제 취소"),
    REFUNDED("환불"),
    DONE("완료")

    ;

    final String desc;

    PaymentStatus(String desc) {
        this.desc = desc;
    }

    public static PaymentStatus from(String status) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid PaymentStatus: " + status));
    }
}
