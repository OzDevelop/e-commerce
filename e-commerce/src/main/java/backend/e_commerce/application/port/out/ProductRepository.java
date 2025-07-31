package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.product.Product;
import backend.e_commerce.infrastructure.out.persistence.product.entity.ProductEntity;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);
    List<Product> saveAll(List<Product> products);
    Optional<Product> findById(Long productId);

    Product update(Product product);

    void deleteById(Long productId);
    boolean existsById(Long productId);
}
