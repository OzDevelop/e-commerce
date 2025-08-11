package backend.e_commerce.application.command.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeleteItemCommand {
    private final Long userId;
    private final Long cartItemId;
}
