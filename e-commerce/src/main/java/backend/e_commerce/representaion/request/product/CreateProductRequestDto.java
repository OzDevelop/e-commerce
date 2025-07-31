package backend.e_commerce.representaion.request.product;

import backend.e_commerce.application.command.product.CreateProductCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CreateProductRequestDto {
    private Long sellerId;

    private String category;
    private String name;
    private String description;
    private int price;
    private int stock; // 초기 재고
    private String brand;
    private String manufacturer;

    public CreateProductCommand toCommand() {
        return CreateProductCommand.builder()
                .sellerId(sellerId)
                .category(category)
                .name(name)
                .description(description)
                .price(price)
                .stock(stock)
                .brand(brand)
                .manufacturer(manufacturer)
                .build();
    }
}
