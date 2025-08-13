package backend.e_commerce.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CartItem {
    private final Long id;
    private final Long productId;
    private int quantity;

    public static CartItem of(Long productId, int quantity) {
        return CartItem.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
    }


    public void changeQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity need positive number");
        }
        this.quantity = quantity;
    }
}
