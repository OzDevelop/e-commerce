package backend.e_commerce.application.command.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddItemCommand {
    private final Long userId;
    private final Long productId;
    private final int quantity;

}
