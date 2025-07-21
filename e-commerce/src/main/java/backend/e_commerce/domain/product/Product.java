package backend.e_commerce.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Product {
    private final Long id;
    private final Long sellerId;

    private String category;
    private String name;
    private String description;
    private int price; // 상품 가격
    private int stock; // 재고 수량
    private ProductStatus status;
    private String brand;
    private String manufacturer;

    public void increaseStock(int amount) {
        int newStockQuantity = stock + amount;
        if (newStockQuantity < 0) {
            throw new IllegalArgumentException("재고를 증가시킬 수 없습니다.");
        }
        stock = newStockQuantity;
    }

    public void decreaseStock(int amount) {
        if(stock < amount) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        stock -= amount;
        if(stock == 0) {
            this.status = ProductStatus.OUT_OF_STOCK;
        }
    }

    public void changeCategory(String newCategory) {
        this.category = newCategory;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeStock(int stock) {
        this.stock = stock;
    }
    public void changeStatus(ProductStatus status) {
        this.status = status;
    }
    public void changeBrand(String brand) {
        this.brand = brand;
    }

    public void changeManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void changeDescription(String description) {
        this.description = description;
    }
}
