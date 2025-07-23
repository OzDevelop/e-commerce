package backend.e_commerce.application.port.in.order;

import backend.e_commerce.application.command.order.CreateOrderCommand;
import backend.e_commerce.domain.order.Order;
import java.util.UUID;

public interface OrderCommandUseCase {
    Order createOrder(CreateOrderCommand command);

    void complete(UUID orderId);
    void cancelItem(UUID orderId, Long orderItemId);
    void cancelAll(UUID orderId);

}
