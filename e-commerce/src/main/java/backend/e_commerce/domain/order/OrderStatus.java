package backend.e_commerce.domain.order;

public enum OrderStatus {
    COMPLETED("완료됨"),
    CANCELLED("취소됨"),
    PENDING_PAYMENT("결제 대기 중"),
    PROCESSING("처리 중"),
    COMPLETED_PAYMENT("결제 완료"),
    /**
     * 배송 관련 상태 변화 후 최종 구매 완료.
     */
    PURCHASE_COMPLETED("구매 완료")

    ;

    final String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }

}
