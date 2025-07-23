package backend.e_commerce.infrastructure.out.persistence.order.mapper;

import backend.e_commerce.domain.order.OrderItem;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderEntity;
import backend.e_commerce.infrastructure.out.persistence.order.entity.OrderItemEntity;

public class OrderItemEntityMapper {
    // OrderItem → OrderItemEntity
    public static OrderItemEntity toEntity(OrderItem item, OrderEntity orderEntity) {
        return OrderItemEntity.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .price(item.getAmount())
                .status(item.getStatus())
                .order(orderEntity)
                .build();
    }


    // OrderItemEntity → OrderItem
    public static OrderItem toDomain(OrderItemEntity entity) {
        return OrderItem.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .amount(entity.getPrice())
                .status(entity.getStatus())
                .build();
    }
}
