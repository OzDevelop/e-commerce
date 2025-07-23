package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.order.Order;
import backend.e_commerce.domain.order.OrderStatus;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    Order update(Order order);

}
