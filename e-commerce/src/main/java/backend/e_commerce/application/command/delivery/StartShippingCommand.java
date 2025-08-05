package backend.e_commerce.application.command.delivery;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class StartShippingCommand {
    private final Long deliveryId;
    private final String trackingNumber;
    private final String deliveryCompany;
//    private final LocalDateTime shippedAt;
}
