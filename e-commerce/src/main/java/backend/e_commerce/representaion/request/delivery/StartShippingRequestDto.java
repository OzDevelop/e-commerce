package backend.e_commerce.representaion.request.delivery;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Getter
public class StartShippingRequestDto {
    private Long deliveryId;
    private String trackingNumber;
    private String deliveryCompany;
//    private final LocalDateTime shippedAt;

}
