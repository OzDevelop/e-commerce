package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.order.Order;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import java.util.Optional;
import java.util.UUID;

public interface OrderPersistencePort {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    Optional<OrderEntity> findByOrderId(UUID id);
    Order update(Order order);

    Optional<Order> findByIdForUpdate(UUID id);
}
