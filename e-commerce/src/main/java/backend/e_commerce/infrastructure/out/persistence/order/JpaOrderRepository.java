package backend.e_commerce.infrastructure.out.persistence.order;

import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {
}
