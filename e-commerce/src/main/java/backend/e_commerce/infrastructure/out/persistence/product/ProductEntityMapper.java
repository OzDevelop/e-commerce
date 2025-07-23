package backend.e_commerce.infrastructure.out.persistence.product;

import backend.e_commerce.domain.product.Product;
import backend.e_commerce.infrastructure.out.persistence.product.entity.ProductEntity;

public class ProductEntityMapper {
    public static ProductEntity fromDomain(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getSellerId(),
                product.getCategory(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getBrand(),
                product.getManufacturer()
        );
    }

    public static Product toDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .sellerId(entity.getSellerId())
                .category(entity.getCategory())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .status(entity.getStatus())
                .brand(entity.getBrand())
                .manufacturer(entity.getManufacturer())
                .build();
    }
}
