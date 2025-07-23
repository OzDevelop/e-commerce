package backend.e_commerce.domain.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    private OrderItem item;


    @BeforeEach
    void setUp() {
        item = new OrderItem(1L, "P-1", 5, 5000);
    }


    @Test
    void 주문상품_초기상태_테스트() {
        assertEquals(OrderStatus.PENDING_PAYMENT, item.getStatus());
    }

    @Test
    void 주문상품_재고_테스트_1() {
        assertTrue(item.hasSufficientStock(100));
    }

    @Test
    void 주문상품_재고_테스트_2() {
        assertFalse(item.hasSufficientStock(1));
    }

    @Test
    void 주문상품_CANCEL_테스트() {
        item.cancel();

        assertEquals(OrderStatus.CANCELLED, item.getStatus());
    }

    @Test
    void 주문상품_CANCEL_예외_테스트() {
        item.cancel();

        assertThrows(IllegalStateException.class, () -> item.cancel());
    }

    @Test
    void 주문상품_상태변경_테스트() {
        item.update(OrderStatus.COMPLETED);

        assertEquals(OrderStatus.COMPLETED, item.getStatus());
    }

    @Test
    void 주문상품_수랑증가_금액갱신_테스트 () {
        item.increaseQuantity(10);

        assertEquals(5000*15, item.getAmount());
    }

    @Test
    void 주문상품_수랑감소_금액갱신_테스트 () {
        item.decreaseQuantity(2);

        assertEquals(5000*3, item.getAmount());
    }

    @Test
    void 주문상품_수량감소_예외_테스트() {
        assertThrows(IllegalArgumentException.class, () -> item.decreaseQuantity(0));
    }

    @Test
    void 주문상품_수량감소_예외_테스트2() {
        assertThrows(IllegalArgumentException.class, () -> item.decreaseQuantity(10));
    }
}