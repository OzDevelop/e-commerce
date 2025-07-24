package backend.e_commerce.domain.order;

import static org.junit.jupiter.api.Assertions.*;

import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.domain.payment.PaymentMethod;
import backend.e_commerce.domain.payment.PaymentStatus;
import backend.e_commerce.domain.user.Address;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

    private Address address;
    private Payment payment;

    private OrderItem item1;
    private OrderItem item2;

    @BeforeEach
    void setUp() {
        address = new Address("서울시", "강남구", "123-45", false);

        item1 = new OrderItem(1L, "product-1", 5, 10000);
        item2 = new OrderItem(2L, "product-2", 5, 5000);
        
        payment = Payment.builder()
                .paymentId(1L)
                .paymentKey("pay-key")
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.COMPLETED)
                .totalAmount(item1.getAmount() + item2.getAmount())
                .canceledAmount(0)
                .build();
    }

    @Test
    void 주문_생성_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1, item2), payment);

        assertEquals(2, order.getOrderItems().size());
        assertEquals(OrderStatus.PENDING_PAYMENT, order.getOrderStatus());
    }

    @Test
    void 주문_생성_예외_테스트_1() {
        assertThrows(IllegalArgumentException.class, () -> Order.createOrder(1L, address, List.of(), payment));
    }

    @Test
    void 주문_생성_예외_테스트_2() {
        assertThrows(IllegalArgumentException.class, () -> Order.createOrder(1L, address, List.of(), null));
    }

    @Test
    void 주문_생성_예외_테스트_3() {
        OrderItem insufficientStockItem = new OrderItem(3L, "product-999", 10, 1000) {
            @Override
            public boolean hasSufficientStock(int availableStock) {
                return false;
            }
        };

        assertThrows(IllegalArgumentException.class, () -> Order.createOrder(1L, address, List.of(insufficientStockItem), payment));
    }

    @Test
    void 주문_완료처리_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1), payment);
        order.completeOrder();

        assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        assertEquals(OrderStatus.COMPLETED, order.getOrderItems().get(0).getStatus());
    }

    @Test
    void 주문_결제실패된_주문_완료처리시_예외_테스트() {
        Payment failPayment = Payment.builder()
                .paymentId(2L)
                .paymentKey("fail-key")
                .paymentMethod(PaymentMethod.BANK_TRANSFER)
                .paymentStatus(PaymentStatus.FAILED)
                .totalAmount(5000)
                .canceledAmount(0)
                .build();

        Order order = Order.createOrder(1L, address, List.of(item1), failPayment);

        assertThrows(IllegalStateException.class, () -> order.completeOrder());
    }

    @Test
    void 주문_부분취소_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1, item2), payment);
        order.orderCancel(1L);

        assertEquals(OrderStatus.PENDING_PAYMENT, order.getOrderStatus());
        assertEquals(OrderStatus.CANCELLED, order.getOrderItems().get(0).getStatus());
    }

    @Test
    void 주문_취소된항목_재취소시_예외_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1), payment);
        order.orderCancel(1L);

        assertThrows(IllegalStateException.class, () -> order.orderCancel(1L));
    }

    @Test
    void 주문_전체취소_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1, item2), payment);
        order.orderAllCancel();

        assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());
        assertEquals(OrderStatus.CANCELLED, order.getOrderItems().get(0).getStatus());
        assertEquals(OrderStatus.CANCELLED, order.getOrderItems().get(1).getStatus());
    }

    @Test
    void 주문_완료된주문_부분취소_불가_테스트() {
        Order order = Order.createOrder(1L, address, List.of(item1), payment);
        order.completeOrder();

        assertThrows(IllegalStateException.class, () -> order.orderCancel(1L));
    }
}