package backend.e_commerce.representaion.request.delivery;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompleteDeliveryRequest {
    private final Long deliveryId;

}
