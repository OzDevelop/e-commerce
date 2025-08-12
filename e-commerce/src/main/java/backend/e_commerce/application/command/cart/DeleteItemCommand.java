package backend.e_commerce.application.command.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class DeleteItemCommand {
    private final Long userId;
    private final Long cartItemId;
}
