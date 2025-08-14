package backend.e_commerce.representaion.request.delivery;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateDeliveryRequest {
    private final UUID orderId;
    private final Long addressId;
    private final String receiverName;
    private final String receiverPhone;
}
