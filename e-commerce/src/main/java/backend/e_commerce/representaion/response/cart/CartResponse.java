package backend.e_commerce.representaion.response.cart;

import backend.e_commerce.domain.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private Long id;
    private Long userId;
    private int itemCount;

    public static CartResponse fromDomain(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .itemCount(cart.getItems().size())
                .build();
    }
}