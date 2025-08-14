package backend.e_commerce.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {
    private final Long id;

    private final Long productId;
    private OrderStatus status; // 주문 상태, 기본값은 결제 대기 중
    private  int quantity; // 수량
    private final int unitPrice;    // 단위 가격

    public OrderItem(Long id, Long productId, int quantity, int unitPrice) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.status = OrderStatus.PENDING_PAYMENT;
    }

    public boolean hasSufficientStock(int availableStock) {
        return quantity <= availableStock;
    }

    public void cancel() {
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("이미 취소된 주문 항목입니다.");
        }

        this.status = OrderStatus.CANCELLED;
    }

    public void update(OrderStatus status) {
        this.status = status;
    }

    public void decreaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("감소 수량은 0보다 커야 합니다.");
        }
        if (this.quantity < quantity) {
            throw new IllegalArgumentException("재고보다 더 많이 감소시킬 수 없습니다.");
        }
        this.quantity -= quantity;
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("증가 수량은 0보다 커야 합니다.");
        }
        this.quantity += quantity;
    }

    public int getAmount() {
        return this.quantity * this.unitPrice;
    }
}
