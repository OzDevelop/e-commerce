package backend.e_commerce.domain.payment;

public enum LedgerType {

    PAYMENT("결제 성공"),     // 결제 성공
    CANCEL("결제 취소"),      // 결제 취소
    REFUND("환불"),      // 환불
    FAILURE("결제 실패");      // 결제 실패

    final String desc;

    LedgerType(String desc) {
        this.desc = desc;
    }
}
