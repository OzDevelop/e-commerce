package backend.e_commerce.infrastructure.out.persistence.cart;

import backend.e_commerce.infrastructure.out.persistence.cart.entity.CartEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserId(Long userId);
}
