package backend.e_commerce.representaion.request.delivery;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ChangeAddressRequestDto {
    private final Long deliveryId;
    private final Long addressId;

}
