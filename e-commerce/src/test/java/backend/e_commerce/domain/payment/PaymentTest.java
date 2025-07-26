package backend.e_commerce.domain.payment;

import static org.junit.jupiter.api.Assertions.*;

import backend.e_commerce.domain.order.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentTest {

    private Payment payment;

    private OrderItem item1;
    private OrderItem item2;

    @BeforeEach
    void setUp() {
        item1 = new OrderItem(1L, "product-1", 5, 10000);
        item2 = new OrderItem(2L, "product-2", 5, 5000);

        payment = Payment.builder()
                .paymentId(1L)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.PENDING)
                .totalAmount(item1.getAmount() + item2.getAmount())
                .canceledAmount(0)
                .build();
    }

    @Test
    void 결제_완료_테스트() {
        assertEquals(PaymentStatus.PENDING, payment.getPaymentStatus());

        payment.markCompleted();
        assertEquals(PaymentStatus.COMPLETED, payment.getPaymentStatus());
    }

    @Test
    void 결제_부분취소_테스트() {
        payment.cancel(50000);

        assertEquals(payment.getCanceledAmount(), 50000);
    }

    @Test
    void 결제_전체취소_테스트() {
        payment.cancel(75000);

        assertEquals(payment.getCanceledAmount(), 75000);
        assertEquals(payment.getPaymentStatus(), PaymentStatus.CANCELLED);
    }

    @Test
    void 결제_취소_실패_테스트() {
        assertThrows(IllegalArgumentException.class, () -> payment.cancel(1000000));
    }

}