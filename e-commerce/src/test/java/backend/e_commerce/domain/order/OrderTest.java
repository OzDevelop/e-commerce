package backend.e_commerce.domain.order;

import static org.junit.jupiter.api.Assertions.*;

import backend.core.common.errorcode.execption.OrderException;
import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.domain.payment.PaymentMethod;
import backend.e_commerce.domain.payment.PaymentStatus;
import backend.e_commerce.domain.user.Address;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {
    private Address address;
    private Payment payment;

    private OrderItem item1;
    private OrderItem item2;
    private Long[] ids;

    @BeforeEach
    void setUp() {
        address = new Address("서울시", "강남구", "123-45", false);

        item1 = new OrderItem(1L, 1L, 5, 10000);
        item2 = new OrderItem(2L, 2L, 5, 5000);

        ids = new Long[]{1L};

        payment = Payment.builder()
                .paymentKey("1L")
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.COMPLETED)
                .totalAmount(item1.getAmount() + item2.getAmount())
                .canceledAmount(0)
                .build();
    }

    @Test
    void 주문_생성_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1, item2));

        assertEquals(2, order.getOrderItems().size());
        assertEquals(OrderStatus.PENDING_PAYMENT, order.getOrderStatus());
    }

    @Test
    void 주문_생성_예외_테스트_1() {
        assertThrows(IllegalArgumentException.class, () -> Order.createOrder(1L, address, List.of()));
    }

    @Test
    void 주문_생성_예외_테스트_2() {
        assertThrows(IllegalArgumentException.class, () -> Order.createOrder(1L, address, List.of()));
    }

    @Test
    void 주문_생성_예외_테스트_3() {
        OrderItem insufficientStockItem = new OrderItem(3L, 100L, 10, 1000) {
            @Override
            public boolean hasSufficientStock(int availableStock) {
                return false;
            }
        };

        assertThrows(IllegalArgumentException.class, () -> Order.createOrder(1L, address, List.of(insufficientStockItem)));
    }

    @Test
    void 주문_완료처리_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1));
        order.completeOrder();

        assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        assertEquals(OrderStatus.COMPLETED, order.getOrderItems().get(0).getStatus());
    }

    @Test
    void 주문_부분취소_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1, item2));
        order.orderCancel(ids);

        assertEquals(OrderStatus.PENDING_PAYMENT, order.getOrderStatus());
        assertEquals(OrderStatus.CANCELLED, order.getOrderItems().get(0).getStatus());
    }

    @Test
    void 주문_취소된항목_재취소시_예외_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1));
        order.orderCancel(ids);

        assertThrows(IllegalStateException.class, () -> order.orderCancel(ids));
    }

    @Test
    void 주문_전체취소_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1, item2));
        order.orderAllCancel();

        assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());
        assertEquals(OrderStatus.CANCELLED, order.getOrderItems().get(0).getStatus());
        assertEquals(OrderStatus.CANCELLED, order.getOrderItems().get(1).getStatus());
    }
}