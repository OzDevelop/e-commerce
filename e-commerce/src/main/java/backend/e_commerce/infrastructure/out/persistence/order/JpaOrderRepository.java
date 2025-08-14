package backend.e_commerce.infrastructure.out.persistence.order;

import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM OrderEntity o WHERE o.id = :orderId")
    Optional<OrderEntity> findByIdForUpdate(@Param("orderId") UUID orderId);
}
