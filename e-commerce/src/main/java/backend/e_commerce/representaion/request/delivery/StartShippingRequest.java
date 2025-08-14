package backend.e_commerce.representaion.request.delivery;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StartShippingRequest {
    private Long deliveryId;
    private String trackingNumber;
    private String deliveryCompany;
//    private final LocalDateTime shippedAt;

}
