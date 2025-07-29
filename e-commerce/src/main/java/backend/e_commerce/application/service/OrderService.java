package backend.e_commerce.application.service;

import backend.e_commerce.application.command.order.CreateOrderCommand;
import backend.e_commerce.application.port.in.order.OrderCommandUseCase;
import backend.e_commerce.application.port.out.OrderRepository;
import backend.e_commerce.domain.order.Order;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderCommandUseCase {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(CreateOrderCommand command) {
        Order order = Order.createOrder(
                command.getUserId(),
                command.getAddress(),
                command.getOrderItems(),
                command.getPayment()
        );
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void complete(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.completeOrder();

        orderRepository.update(order);
    }

    @Override
    @Transactional
    public void cancelItem(UUID orderId, Long[] orderItemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.orderCancel(orderItemId);

        orderRepository.update(order);
    }

    @Override
    @Transactional
    public void cancelAll(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.orderAllCancel();
        orderRepository.update(order);
    }

    public Order getOrderInfo(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("Order not found"));

    }
}
