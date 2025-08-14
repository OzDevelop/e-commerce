package backend.e_commerce.representaion.request.chekcout;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class CheckoutSelectedRequest {
    private final Long userId;
    private final Long addressId;
    private final List<Long> selectedItemIds;
}
