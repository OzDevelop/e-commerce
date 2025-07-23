package backend.e_commerce.application.command.product;

import backend.e_commerce.domain.product.Product;
import backend.e_commerce.domain.product.ProductStatus;

public class ProductCommandMapper {

    public static Product toDomain(CreateProductCommand command) {
        return Product.builder()
                .sellerId(command.getSellerId())
                .category(command.getCategory())
                .name(command.getName())
                .description(command.getDescription())
                .price(command.getPrice())
                .stock(command.getStock())
                .status(ProductStatus.AVAILABLE)
                .brand(command.getBrand())
                .manufacturer(command.getManufacturer())
                .build();
    }

    public static void applyUpdate(Product product, UpdateProductCommand command) {
        command.getName().ifPresent(product::changeName);
        command.getDescription().ifPresent(product::changeDescription);
        command.getPrice().ifPresent(product::changePrice);
        command.getStock().ifPresent(product::changeStock);
        command.getStatus().ifPresent(product::changeStatus);
        command.getBrand().ifPresent(product::changeBrand);
        command.getManufacturer().ifPresent(product::changeManufacturer);
    }

}
