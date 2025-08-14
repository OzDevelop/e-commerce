package backend.e_commerce.representaion.request.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddItemRequest {
    private Long productId;
    private int quantity;
}
