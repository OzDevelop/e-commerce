package backend.e_commerce.application.port.in.Delivery;

import backend.e_commerce.application.command.delivery.CreateDeliveryCommand;
import backend.e_commerce.application.command.delivery.StartShippingCommand;
import backend.e_commerce.domain.delivery.Delivery;
import backend.e_commerce.domain.user.Address;
import java.time.LocalDateTime;

public interface DeliveryCommandUseCase {
    Delivery createDelivery(CreateDeliveryCommand command);
    Delivery startShipping(StartShippingCommand command);
    Delivery completeDelivery(Long deliveryId);
    Delivery changeAddress(Long deliveryId, Long addressId);
}
