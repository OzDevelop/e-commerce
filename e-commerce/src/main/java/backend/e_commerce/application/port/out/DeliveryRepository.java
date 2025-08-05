package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.delivery.Delivery;
import java.util.List;
import java.util.UUID;

public interface DeliveryRepository {
    Delivery save(Delivery delivery);
    Delivery findById(Long deliveryId);
    Delivery update(Delivery delivery);

//    List<Delivery> findByOrderId(UUID orderId);
}
