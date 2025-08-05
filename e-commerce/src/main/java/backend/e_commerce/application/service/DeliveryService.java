package backend.e_commerce.application.service;

import backend.e_commerce.application.command.delivery.CreateDeliveryCommand;
import backend.e_commerce.application.command.delivery.StartShippingCommand;
import backend.e_commerce.application.port.in.Delivery.DeliveryCommandUseCase;
import backend.e_commerce.application.port.in.Delivery.DeliveryQueryUseCase;
import backend.e_commerce.application.port.out.DeliveryRepository;
import backend.e_commerce.application.port.out.OrderRepository;
import backend.e_commerce.domain.delivery.Delivery;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.domain.user.Address;
import backend.e_commerce.infrastructure.out.persistence.user.AddressEntityMapper;
import backend.e_commerce.infrastructure.out.persistence.user.JpaAddressRepository;
import backend.e_commerce.infrastructure.out.persistence.user.entity.AddressEntity;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DeliveryService implements DeliveryCommandUseCase, DeliveryQueryUseCase {
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final JpaAddressRepository jpaAddressRepository;


    @Override
    public Delivery createDelivery(CreateDeliveryCommand command) {
        AddressEntity addressEntity = jpaAddressRepository.findById(command.getAddressId())
                .orElseThrow();

        log.info("CreateDeliveryCommand 수신: orderId={}, addressId={}, receiverName={}, receiverPhone={}",
                command.getOrderId(), command.getAddressId(),
                command.getReceiverName(), command.getReceiverPhone());

        Address address = AddressEntityMapper.fromEntityToDomain(addressEntity);


         Delivery delivery = Delivery.createDelivery(
                 command.getOrderId(),
                 address,
                 command.getReceiverName(),
                 command.getReceiverPhone()
                 );

        System.out.println("getReceiverName >>" +  delivery.getReceiverName());

        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery startShipping(StartShippingCommand command) {
        Delivery delivery = deliveryRepository.findById(command.getDeliveryId());

        System.out.println("delivery.getId >> "+delivery.getId());

        delivery = delivery.startShipping(command.getTrackingNumber(), command.getDeliveryCompany(), LocalDateTime.now());

        System.out.println("delivery.getId >> "+delivery.getId());
        System.out.println("delivery.getTrackingNumber >> "+delivery.getTrackingNumber());
        System.out.println("delivery.getDeliveryCompany >> "+delivery.getDeliveryCompany());
        System.out.println("delivery.getTrackingNumber >> "+delivery.getTrackingNumber());
        System.out.println(delivery.getStatus());
        System.out.println(delivery.getShippedAt());



        return deliveryRepository.update(delivery);
    }

    @Override
    public Delivery completeDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId);

        delivery = delivery.completeDelivery(LocalDateTime.now());

        Order order = orderRepository.findById(delivery.getOrderId())
                .orElseThrow();

        order.completeDelivery();
        orderRepository.update(order);

        return deliveryRepository.update(delivery);
    }

    @Override
    public Delivery changeAddress(Long deliveryId, Long addressId) {
        Delivery delivery = deliveryRepository.findById(deliveryId);

        // TODO - 해당 User id 검증 로직 추가

        AddressEntity addressEntity = jpaAddressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));
        Address newAddress = AddressEntityMapper.fromEntityToDomain(addressEntity);

        delivery = delivery.changeAddress(newAddress);

        return deliveryRepository.update(delivery);
    }

//    @Override
//    public List<Delivery> getDeliveryByOrderId(UUID orderId) {
//        return deliveryRepository.findByOrderId(orderId);
//    }
}
