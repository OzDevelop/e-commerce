package backend.e_commerce.application.service;

import backend.core.common.errorcode.errorcode.OrderErrorCode;
import backend.core.common.errorcode.execption.OrderException;
import backend.e_commerce.application.command.order.CreateOrderCommand;
import backend.e_commerce.application.port.in.order.OrderCommandUseCase;
import backend.e_commerce.application.port.out.OrderRepository;
import backend.e_commerce.application.port.out.ProductRepository;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.domain.order.OrderItem;
import backend.e_commerce.domain.product.Product;
import backend.e_commerce.domain.product.ProductStatus;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderCommandUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public Order createOrder(CreateOrderCommand command) {
        List<OrderItem> validatedItems = command.getOrderItems().stream()
                .map(this::validateOrderItem)
                .toList();

        Order order = Order.createOrder(
                command.getUserId(),
                command.getAddress(),
                validatedItems
        );

        // TODO - payment 추가 타이밍  설정

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void complete(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));
        order.completeOrder();

        orderRepository.update(order);
    }

    @Override
    @Transactional
    public void cancelItem(UUID orderId, Long[] orderItemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        order.orderCancel(orderItemId);

        orderRepository.update(order);
    }

    @Override
    @Transactional
    public void cancelAll(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));
        order.orderAllCancel();
        orderRepository.update(order);
    }

    public Order getOrderInfo(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

    }

    /** -- Private Method -- **/
    private OrderItem validateOrderItem(OrderItem item) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new OrderException(OrderErrorCode.PRODUCT_NOT_FOUND,
                        Map.of("productId", item.getProductId())));

        if (!item.hasSufficientStock(product.getStock())) {
            throw new OrderException(OrderErrorCode.INSUFFICIENT_STOCK,
                    Map.of("productId", product.getId(), "stock", product.getStock()));
        }
        if (product.getStatus() == ProductStatus.DISCONTINUED) {
            throw new OrderException(OrderErrorCode.PRODUCT_DISCONTINUED,
                    Map.of("productId", product.getId()));
        }
        if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
            throw new OrderException(OrderErrorCode.PRODUCT_OUT_OF_STOCK,
                    Map.of("productId", product.getId()));
        }

        return OrderItem.builder()
                .productId(product.getId())
                .quantity(item.getQuantity())
                .unitPrice(product.getPrice())
                .build();
    }
}
