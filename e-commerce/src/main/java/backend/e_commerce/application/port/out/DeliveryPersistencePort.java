package backend.e_commerce.application.port.out;

import backend.e_commerce.domain.delivery.Delivery;

public interface DeliveryPersistencePort {
    Delivery save(Delivery delivery);
    Delivery findById(Long deliveryId);
    Delivery update(Delivery delivery);

//    List<Delivery> findByOrderId(UUID orderId);
}
