package backend.e_commerce.application.command.delivery;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CreateDeliveryCommand {
    private final UUID orderId;
    private final Long addressId;
    private final String receiverName;
    private final String receiverPhone;
}
