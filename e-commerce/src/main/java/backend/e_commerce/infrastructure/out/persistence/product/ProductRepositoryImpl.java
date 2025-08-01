package backend.e_commerce.infrastructure.out.persistence.product;

import backend.e_commerce.application.port.out.ProductRepository;
import backend.e_commerce.domain.product.Product;
import backend.e_commerce.domain.user.User;
import backend.e_commerce.infrastructure.out.persistence.product.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = ProductEntityMapper.fromDomain(product);

        jpaProductRepository.save(productEntity);
        return ProductEntityMapper.toDomain(productEntity);
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        List<ProductEntity> productEntities = products.stream()
                .map(ProductEntityMapper::fromDomain)
                .collect(Collectors.toList());
        jpaProductRepository.saveAll(productEntities);

        return productEntities.stream()
                .map(ProductEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Product update(Product product) {
        ProductEntity productEntity = jpaProductRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + product.getId()));

        productEntity.setStock(product.getStock());

        return ProductEntityMapper.toDomain(productEntity);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return jpaProductRepository.findById(productId)
                .map(ProductEntityMapper::toDomain);
    }

    @Override
    public void deleteById(Long productId) {
        jpaProductRepository.deleteById(productId);
    }

    @Override
    public boolean existsById(Long productId) {
        return jpaProductRepository.existsById(productId);
    }
}
