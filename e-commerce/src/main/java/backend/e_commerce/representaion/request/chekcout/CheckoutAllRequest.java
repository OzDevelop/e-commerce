package backend.e_commerce.representaion.request.chekcout;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CheckoutAllRequest {
    private final Long addressId;
    private final Long userId;

}
