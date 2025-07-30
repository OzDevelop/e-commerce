package backend.e_commerce.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.e_commerce.application.command.order.CreateOrderCommand;
import backend.e_commerce.application.port.out.OrderRepository;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.domain.order.OrderItem;
import backend.e_commerce.domain.order.OrderStatus;
import backend.e_commerce.domain.payment.Payment;
import backend.e_commerce.domain.payment.PaymentMethod;
import backend.e_commerce.domain.payment.PaymentStatus;
import backend.e_commerce.domain.user.Address;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Address address;
    private List<OrderItem> orderItems;
    private Payment payment;
    private CreateOrderCommand createOrderCommand;

    private Order realOrder;

    @BeforeEach
    void setUp() {
        address = new Address("서울", "강남대로", "12345", false);

        orderItems = List.of(
                new OrderItem(1L, "P-1", 5, 5000),
                new OrderItem(2L, "P-2", 10, 50000)
        );

        payment = new Payment(null, PaymentMethod.CREDIT_CARD, PaymentStatus.COMPLETED, 60000, 0);

        createOrderCommand = new CreateOrderCommand(
                1L,
                address,
                orderItems,
                payment
        );

        realOrder = Order.createOrder(
                createOrderCommand.getUserId(),
                createOrderCommand.getAddress(),
                createOrderCommand.getOrderItems(),
                createOrderCommand.getPayment()
        );
    }

    @Test
    void 주문_생성_테스트() {
        when(orderRepository.save(any(Order.class))).thenReturn(realOrder);

        Order result = orderService.createOrder(createOrderCommand);

        verify(orderRepository, times(1)).save(any(Order.class));

        assertEquals(realOrder, result);
        assertEquals(createOrderCommand.getUserId(), result.getUserId());
        assertEquals(result.getOrderItems().get(0).getProductId(), createOrderCommand.getOrderItems().get(0).getProductId());
    }

    @Test
    void 주문_완료_테스트() {
        when(orderRepository.update(any(Order.class))).thenReturn(realOrder);
        when(orderRepository.findById(realOrder.getId())).thenReturn(Optional.ofNullable(realOrder));

        orderService.complete(realOrder.getId());

        verify(orderRepository, times(1)).update(any(Order.class));
        verify(orderRepository, times(1)).findById(realOrder.getId());

        assertEquals(realOrder.getOrderStatus(), OrderStatus.COMPLETED);
    }

    @Test
    void 주문_전체취소_테스트() {
        when(orderRepository.update(any(Order.class))).thenReturn(realOrder);
        when(orderRepository.findById(realOrder.getId())).thenReturn(Optional.ofNullable(realOrder));

        orderService.cancelAll(realOrder.getId());

        verify(orderRepository, times(1)).update(any(Order.class));
        verify(orderRepository, times(1)).findById(realOrder.getId());

        assertEquals(realOrder.getOrderStatus(), OrderStatus.CANCELLED);
        assertEquals(realOrder.getOrderItems().get(0).getStatus(), OrderStatus.CANCELLED);
        assertEquals(realOrder.getOrderItems().get(1).getStatus(), OrderStatus.CANCELLED);
    }

    @Test
    void 주문_일부상품취소_테스트() {
        Long cancelProductId = orderItems.get(0).getId();

        when(orderRepository.findById(realOrder.getId())).thenReturn(Optional.of(realOrder));
        when(orderRepository.update(any(Order.class))).thenReturn(realOrder);

        orderService.cancelItem(realOrder.getId(), cancelProductId);

        verify(orderRepository).findById(realOrder.getId());
        verify(orderRepository).update(any(Order.class));

        assertEquals(realOrder.getOrderItems().get(0).getStatus(), OrderStatus.CANCELLED);
    }

    //TODO - 실패케이스 추가 예정
}