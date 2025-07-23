package backend.e_commerce.infrastructure.out.persistence.product;

import backend.e_commerce.infrastructure.out.persistence.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
}
