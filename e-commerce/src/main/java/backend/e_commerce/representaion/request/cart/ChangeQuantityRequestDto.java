package backend.e_commerce.representaion.request.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ChangeQuantityRequestDto {
    private final Long userId;
    private final Long cartItemId;
    private final int quantity;
}
