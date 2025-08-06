package backend.e_commerce.domain.delivery;

public enum DeliveryStatus {
    READY("배송 준비"),
    SHIPPING("배송중"),
    DELIVERED("배송완료");

    final String desc;

    DeliveryStatus(String desc) {
        this.desc = desc;
    }
}
