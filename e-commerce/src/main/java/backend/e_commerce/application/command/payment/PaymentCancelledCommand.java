package backend.e_commerce.application.command.payment;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCancelledCommand {
    private UUID orderId;
    private String paymentKey;
    private String cancelReason;

    private Long[] itemIds;
    private int cancellationAmount;
}
