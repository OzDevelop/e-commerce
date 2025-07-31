package backend.e_commerce.infrastructure.out.persistence.order.mapper;

import backend.e_commerce.domain.order.Order;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderItemEntity;
import backend.e_commerce.infrastructure.out.persistence.payment.PaymentEntityMapper;
import backend.e_commerce.infrastructure.out.persistence.user.AddressEntityMapper;
import java.util.List;
import java.util.stream.Collectors;

public class OrderEntityMapper {
    public static OrderEntity toEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .userId(order.getUserId())
                .address(AddressEntityMapper.toEntity(order.getOrderAddress()))
                .status(order.getOrderStatus())
                .build();

        List<OrderItemEntity> itemEntities = order.getOrderItems().stream()
                .map(item -> OrderItemEntityMapper.toEntity(item, orderEntity))
                .collect(Collectors.toList());

        orderEntity.getOrderItems().addAll(itemEntities);

        return orderEntity;
    }

    // OrderEntity → Order
    public static Order toDomain(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .orderStatus(entity.getStatus())
                .orderAddress(AddressEntityMapper.toDomain(entity.getAddress()))
                .orderItems(entity.getOrderItems().stream()
                        .map(OrderItemEntityMapper::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }
}
