package backend.e_commerce.domain.order;

import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.domain.user.Address;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Order {
    private final UUID id;
    private final Long userId;  // 구매자 ID

    private final Address orderAddress; // 배송지 주소
    private final List<OrderItem> orderItems; // 주문 상품
    private OrderStatus orderStatus; // 주문상태
    private final Payment payment; // 결제정보


    public static Order createOrder(Long userId, Address address, List<OrderItem> orderItems, Payment payment) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException("주문 항목이 비어 있을 수 없습니다.");
        }
        if (payment == null) {
            throw new IllegalArgumentException("결제 정보가 필요합니다.");
        }

        for (OrderItem orderItem : orderItems) {
            if (!orderItem.hasSufficientStock(orderItem.getQuantity())) {
                throw new IllegalArgumentException("상품 " + orderItem.getProductId() + "의 재고가 부족합니다.");
            }
        }

        return Order.builder()
                .userId(userId)
                .orderAddress(address)
                .orderItems(orderItems)
                .orderStatus(OrderStatus.PENDING_PAYMENT)
                .payment(payment)
                .build();
    }

    public void completeOrder() {
        if(orderStatus == OrderStatus.PROCESSING) {
            throw new IllegalStateException("주문이 처리중입니다.");
        }

        if (!isPaymentSuccess()) {
            throw new IllegalStateException("결제가 완료되지 않은 주문은 완료할 수 없습니다.");
        }

        orderStatus = OrderStatus.COMPLETED;
        this.orderItems.forEach(item -> item.update(OrderStatus.COMPLETED));
    }

    // 특정 제품만 취소
    public void orderCancel(Long orderItemId) {
        if(this.orderStatus == OrderStatus.COMPLETED) {
            throw new IllegalStateException("이미 완료된 주문은 취소할 수 없습니다.");
        }

        OrderItem item = this.orderItems.stream()
                .filter(orderItem -> orderItem.getId().equals(orderItemId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 주문 상품이 존재하지 않습니다."));

        if(item.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("이미 취소된 상품입니다.");
        }

        item.cancel();
        if (allItemsCancelled()) {
            this.orderStatus = OrderStatus.CANCELLED;
        }
    }

    public void orderAllCancel() {
        if (this.orderStatus == OrderStatus.COMPLETED) {
            throw new IllegalStateException("이미 완료된 주문은 취소할 수 없습니다.");
        }
        this.orderStatus = OrderStatus.CANCELLED;
        this.orderItems.forEach(OrderItem::cancel);
    }


    private boolean isPaymentSuccess() {
        return payment.isSuccess();
    }


    private boolean allItemsCancelled() {
        return this.orderItems.stream()
                .allMatch(item -> item.getStatus() == OrderStatus.CANCELLED);
    }
}
