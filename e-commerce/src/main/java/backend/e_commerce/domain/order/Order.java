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
    private String paymentKey;


    public static Order createOrder(Long userId, Address address, List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException("주문 항목이 비어 있을 수 없습니다.");
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
                .build();
    }

    public void completeOrder() {
        if(orderStatus == OrderStatus.PROCESSING) {
            throw new IllegalStateException("주문이 처리중입니다.");
        }

        orderStatus = OrderStatus.COMPLETED;
        this.orderItems.forEach(item -> item.update(OrderStatus.COMPLETED));
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    // 특정 제품만 취소
    public void orderCancel(Long[] itemIds) {

        if(this.orderStatus == OrderStatus.PURCHASE_COMPLETED) {
            throw new IllegalStateException("이미 완료된 주문은 취소할 수 없습니다.");
        }

        int totalCancelAmount = 0;

        System.out.println("OrderItems: " + this.orderItems.stream().map(OrderItem::getId).toList());

        for(long orderItemId : itemIds) {
            OrderItem item = this.orderItems.stream()
                    .filter(orderItem -> orderItem.getId().equals(orderItemId)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("해당 주문 상품이 존재하지 않습니다."));

            if(item.getStatus() == OrderStatus.CANCELLED) {
                throw new IllegalStateException("이미 취소된 상품입니다.");
            }
            item.cancel();
            totalCancelAmount += item.getAmount();
        }

        if (allItemsCancelled()) {
            this.orderStatus = OrderStatus.CANCELLED;
        }
    }

    public void orderAllCancel() {
        if (this.orderStatus == OrderStatus.PURCHASE_COMPLETED) {
            throw new IllegalStateException("이미 완료된 주문은 취소할 수 없습니다.");
        }

        this.orderStatus = OrderStatus.CANCELLED;
        this.orderItems.forEach(OrderItem::cancel);
    }


    private boolean allItemsCancelled() {
        return this.orderItems.stream()
                .allMatch(item -> item.getStatus() == OrderStatus.CANCELLED);
    }
}
