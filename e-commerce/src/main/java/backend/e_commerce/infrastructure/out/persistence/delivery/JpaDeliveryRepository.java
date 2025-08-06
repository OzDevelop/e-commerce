package backend.e_commerce.infrastructure.out.persistence.delivery;

import backend.e_commerce.infrastructure.out.persistence.delivery.entity.DeliveryEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
    Optional<DeliveryEntity> findByOrderId(UUID orderId);
}
