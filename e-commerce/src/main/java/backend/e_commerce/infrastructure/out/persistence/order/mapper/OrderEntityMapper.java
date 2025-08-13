package backend.e_commerce.infrastructure.out.persistence.order.mapper;

import backend.e_commerce.domain.order.Order;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderItemEntity;
import backend.e_commerce.infrastructure.out.persistence.user.AddressEntityMapper;
import java.util.List;
import java.util.stream.Collectors;

public class OrderEntityMapper {
    public static OrderEntity fromDomainToEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .userId(order.getUserId())
                .address(AddressEntityMapper.fromDomainToEntity(order.getOrderAddress()))
                .status(order.getOrderStatus())
                .integrityHash(order.getIntegrityHash())
                .build();

        List<OrderItemEntity> itemEntities = order.getOrderItems().stream()
                .map(item -> OrderItemEntityMapper.fromDomainToEntity(item, orderEntity))
                .collect(Collectors.toList());

        orderEntity.getOrderItems().addAll(itemEntities);

        orderEntity.calculateAndSetIntegrityHash();

        return orderEntity;
    }

    public static Order fromEntityToDomain(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .orderStatus(entity.getStatus())
                .orderAddress(AddressEntityMapper.fromEntityToDomain(entity.getAddress()))
                .integrityHash(entity.getIntegrityHash())
                .orderItems(entity.getOrderItems().stream()
                        .map(OrderItemEntityMapper::fromEntityToDomain)
                        .collect(Collectors.toList()))
                .build();
    }
}
