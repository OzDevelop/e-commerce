package backend.e_commerce.domain.delivery;

import static backend.e_commerce.domain.delivery.DeliveryStatus.READY;

import backend.e_commerce.domain.user.Address;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class Delivery {
    private final Long id;
    private final UUID orderId;

    private final DeliveryStatus status;
    private final String trackingNumber;
    private final String deliveryCompany;

    private final Address address;

    private final String receiverName;
    private final String receiverPhone;

    private final LocalDateTime shippedAt;  // 배송 시작일
    private final LocalDateTime deliveredAt; // 배송 완료일

    public static Delivery createDelivery(UUID orderId, Address address, String receiverName, String receiverPhone) {
        return Delivery.builder()
                .orderId(orderId)
                .status(READY)
                .address(address)
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .build();
    }


    // 배송 시작
    public Delivery startShipping(String trackingNumber, String deliveryCompany, LocalDateTime shippedAt) {
        if (this.status != READY) {
            throw new IllegalStateException("배송 시작은 READY 상태에서만 가능합니다.");
        }
        return Delivery.builder()
                .id(this.id)
                .orderId(this.orderId)
                .status(DeliveryStatus.SHIPPING)
                .trackingNumber(trackingNumber)
                .deliveryCompany(deliveryCompany)
                .address(this.address)
                .receiverName(this.receiverName)
                .receiverPhone(this.receiverPhone)
                .shippedAt(shippedAt)
                .deliveredAt(this.deliveredAt)
                .build();
    }

    // 배송 완료
    public Delivery completeDelivery(LocalDateTime deliveredAt) {
        if (this.status != DeliveryStatus.SHIPPING) {
            throw new IllegalStateException("배송 상태가 SHIPPING일 때만 완료 처리할 수 있습니다.");
        }

        return Delivery.builder()
                .id(this.id)
                .orderId(this.orderId)
                .status(DeliveryStatus.DELIVERED)
                .trackingNumber(this.trackingNumber)
                .deliveryCompany(this.deliveryCompany)
                .address(this.address)
                .receiverName(this.receiverName)
                .receiverPhone(this.receiverPhone)
                .shippedAt(this.shippedAt)
                .deliveredAt(deliveredAt)
                .build();
    }

    // 배송지 변경
    public Delivery changeAddress(Address newAddress) {
        if (this.status != DeliveryStatus.READY) {
            throw new IllegalStateException("배송 준비 상태에서만 주소를 변경할 수 있습니다.");
        }
        return Delivery.builder()
                .id(this.id)
                .orderId(this.orderId)
                .status(this.status)
                .trackingNumber(this.trackingNumber)
                .deliveryCompany(this.deliveryCompany)
                .address(newAddress)
                .receiverName(this.receiverName)
                .receiverPhone(this.receiverPhone)
                .shippedAt(this.shippedAt)
                .deliveredAt(this.deliveredAt)
                .build();
    }
}
