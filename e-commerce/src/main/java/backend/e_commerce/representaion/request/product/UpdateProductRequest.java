package backend.e_commerce.representaion.request.product;

import backend.e_commerce.application.command.product.UpdateProductCommand;
import backend.e_commerce.domain.product.ProductStatus;
import java.util.Optional;
import lombok.Getter;

@Getter
public class UpdateProductRequest {
    private String category;
    private String name;
    private String description;
    private Integer price;
    private ProductStatus status;
    private Integer stock;
    private String brand;
    private String manufacturer;

    public UpdateProductCommand toCommand() {
        return UpdateProductCommand.builder()
                .category(Optional.ofNullable(category))
                .name(Optional.ofNullable(name))
                .description(Optional.ofNullable(description))
                .price(Optional.ofNullable(price))
                .status(Optional.ofNullable(status))
                .stock(Optional.ofNullable(stock))
                .brand(Optional.ofNullable(brand))
                .manufacturer(Optional.ofNullable(manufacturer))
                .build();

    }
}
