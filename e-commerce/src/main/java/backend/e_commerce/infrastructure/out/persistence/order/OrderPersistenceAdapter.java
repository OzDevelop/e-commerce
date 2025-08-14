package backend.e_commerce.infrastructure.out.persistence.order;

import backend.core.common.errorcode.errorcode.OrderErrorCode;
import backend.core.common.errorcode.execption.OrderException;
import backend.e_commerce.application.port.out.OrderPersistencePort;
import backend.e_commerce.domain.order.Order;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderItemEntity;
import backend.e_commerce.infrastructure.out.persistence.order.mapper.OrderEntityMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistencePort {

    private final JpaOrderRepository jpaOrderRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = OrderEntityMapper.fromDomainToEntity(order);

        OrderEntity saved = jpaOrderRepository.save(orderEntity);
        return OrderEntityMapper.fromEntityToDomain(saved);
    }

    @Override
    public Order update(Order order) {
        if (order.getId() == null) {
            throw new OrderException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        OrderEntity entity = jpaOrderRepository.findById(order.getId())
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        // 상태 업데이트 (변경감지)
        entity.setStatus(order.getOrderStatus());
        entity.setPaymentKey(order.getPaymentKey());
        for (OrderItemEntity item : entity.getOrderItems()) {
//            item.setStatus(order.getOrderStatus());
            order.getOrderItems().stream()
                    .filter(domainItem -> domainItem.getId().equals(item.getId()))
                    .findFirst()
                    .ifPresent(domainItem -> item.setStatus(domainItem.getStatus()));
        }

        return OrderEntityMapper.fromEntityToDomain(entity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaOrderRepository.findById(id).map(OrderEntityMapper::fromEntityToDomain);
    }

    @Override
    public Optional<OrderEntity> findByOrderId(UUID id) {
        return jpaOrderRepository.findById(id);
    }
}
