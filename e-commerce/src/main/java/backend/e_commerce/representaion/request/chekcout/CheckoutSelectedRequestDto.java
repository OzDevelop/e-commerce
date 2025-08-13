package backend.e_commerce.representaion.request.chekcout;

import backend.e_commerce.domain.order.OrderItem;
import backend.e_commerce.domain.user.Address;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class CheckoutSelectedRequestDto {
    private final Long userId;
    private final Long addressId;
    private final List<Long> selectedItemIds;
}
