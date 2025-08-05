package backend.e_commerce.representaion.request.delivery;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompleteDeliveryRequestDto {
    private final Long deliveryId;

}
