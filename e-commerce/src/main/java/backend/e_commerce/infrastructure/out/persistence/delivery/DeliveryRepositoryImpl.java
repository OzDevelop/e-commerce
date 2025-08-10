package backend.e_commerce.infrastructure.out.persistence.delivery;

import backend.core.common.errorcode.errorcode.DeliveryErrorCode;
import backend.core.common.errorcode.execption.DeliveryException;
import backend.e_commerce.application.port.out.DeliveryRepository;
import backend.e_commerce.application.port.out.OrderRepository;
import backend.e_commerce.domain.delivery.Delivery;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.infrastructure.out.persistence.delivery.entity.DeliveryEntity;
import backend.e_commerce.infrastructure.out.persistence.delivery.mapper.DeliveryMapper;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import backend.e_commerce.infrastructure.out.persistence.order.mapper.OrderEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final JpaDeliveryRepository jpaDeliveryRepository;
    private final OrderRepository orderRepository;

    @Override
    public Delivery save(Delivery delivery) {
        OrderEntity orderEntity = orderRepository.findByOrderId(delivery.getOrderId())
                .orElseThrow(() -> new DeliveryException(
                        DeliveryErrorCode.ORDER_NOT_FOUND,
                        Map.of("orderId", delivery.getOrderId())
                ));


        DeliveryEntity deliveryEntity = DeliveryMapper.fromDomainToEntity(delivery, orderEntity);

        jpaDeliveryRepository.save(deliveryEntity);

        return DeliveryMapper.fromEntityToDomain(deliveryEntity);
    }

    @Override
    public Delivery findById(Long deliveryId) {
        DeliveryEntity entity = jpaDeliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryException(
                        DeliveryErrorCode.DELIVERY_NOT_FOUND,
                        Map.of("orderId", deliveryId)
                ));

        System.out.println("entity.getId in Impl >>>>"+entity.getId());

        return DeliveryMapper.fromEntityToDomain(entity);
    }

    @Override
    public Delivery update(Delivery delivery) {

        log.info("업데이트 대상 delivery ID: {}", delivery.getId());

        // 1. 기존 엔티티 조회
        DeliveryEntity entity = jpaDeliveryRepository.findById(delivery.getId())
                .orElseThrow(() -> new DeliveryException(
                        DeliveryErrorCode.DELIVERY_NOT_FOUND,
                        Map.of("orderId", delivery.getOrderId())
                ));

        entity = DeliveryMapper.toEntity(delivery, entity);

        System.out.println("entity.getId >> "+entity.getId());
        System.out.println("entity.getTrackingNumber >> "+entity.getTrackingNumber());
        System.out.println("entity.getDeliveryCompany >> "+entity.getDeliveryCompany());
        System.out.println("entity.getTrackingNumber >> "+entity.getTrackingNumber());
        System.out.println(delivery.getStatus());
        System.out.println(delivery.getShippedAt());

        // 3. JPA가 dirty checking 으로 자동 반영
        return DeliveryMapper.fromEntityToDomain(entity);
    }

    //TODO - 단일 조회, 전체 조회로 구현
//    @Override
//    public List<Delivery> findByOrderId(UUID orderId) {
//        DeliveryEntity entity = jpaDeliveryRepository.findByOrderId(orderId)
//                .orElseThrow(() -> new EntityNotFoundException("Delivery not found for orderId: " + orderId));
//
//        return DeliveryMapper.fromEntityToDomain(entity);
//    }
}
