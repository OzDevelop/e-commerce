package backend.e_commerce.application.command.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProductCommand {
    private Long sellerId;

    private String category;
    private String name;
    private String description;
    private int price;
    private int stock; // 초기 재고
    private String brand;
    private String manufacturer;

}
