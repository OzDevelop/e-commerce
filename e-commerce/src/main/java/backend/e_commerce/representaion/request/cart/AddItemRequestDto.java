package backend.e_commerce.representaion.request.cart;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddItemRequestDto {
    private Long productId;
    private int quantity;
}
