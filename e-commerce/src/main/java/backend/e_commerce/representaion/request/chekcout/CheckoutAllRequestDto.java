package backend.e_commerce.representaion.request.chekcout;

import backend.e_commerce.domain.user.Address;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class CheckoutAllRequestDto {
    private final Long addressId;
    private final Long userId;

}
